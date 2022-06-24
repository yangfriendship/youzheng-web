package me.youzheng.userservice.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import me.youzheng.userservice.exception.UserException;
import me.youzheng.userservice.security.domain.JwtResult;
import me.youzheng.userservice.user.domain.Role;
import me.youzheng.userservice.security.domain.UserContext;
import me.youzheng.userservice.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class JwtProvider {

    public static final String SUB = "Authentication";
    public static final String AUTHENTICATION_KEY = "auth";
    public static final String HOST = "http//me.youzheng.youzheng-web";
    public static final String TOKEN_PREFIX = "bearer ";

    private final String secret;
    private final long expireTime;
    private Key key;

    public JwtProvider(String secret, Long expireTimePerSeconds) {
        this.secret = secret;
        this.expireTime = expireTimePerSeconds * 1000L;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Authentication authentication) {

        UserContext context = (UserContext) authentication.getPrincipal();
        User user = context.getUser();

        return TOKEN_PREFIX + Jwts.builder()
            .setSubject(SUB)
            .setIssuer(HOST)
            .setAudience(user.getEmail())
            .claim(AUTHENTICATION_KEY, user.getRole().name())
            .claim("userNo", user.getUserNo())
            .claim("loginId", user.getLoginId())
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(parseToDate())
            .compact();
    }

    private Date parseToDate() {
        long now = (new Date().getTime());
        return new Date(now + this.expireTime);
    }

    public Authentication resolveToken(String token) {

        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        String loginId = (String) claims.get("loginId");
        String role = (String) claims.get("auth");
        Integer id = (Integer) claims.get("id");

        User user = User.builder()
            .userNo(id)
            .role(Role.of(role))
            .loginId(loginId)
            .build();

        return getUserToken(user);
    }

    private UsernamePasswordAuthenticationToken getUserToken(User user) {
        UserContext userContext = new UserContext(user);
        return new UsernamePasswordAuthenticationToken(userContext, null,
            userContext.getAuthorities());
    }

    public JwtResult validate(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return JwtResult.VALID;
        } catch (MalformedJwtException e) {
            throw new UserException("[ERROR] 유효한 토큰이 아닙니다.");
        } catch (ExpiredJwtException e) {
            return JwtResult.EXPIRED;
        } catch (RuntimeException e) {
            throw e;
        }
    }

}