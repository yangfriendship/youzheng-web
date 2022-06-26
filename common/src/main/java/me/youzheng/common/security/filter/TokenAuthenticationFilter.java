package me.youzheng.common.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.youzheng.common.security.domain.JwtResult;
import me.youzheng.common.security.util.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {

        checkToken(request);

        chain.doFilter(request, response);
    }

    private void checkToken(HttpServletRequest request) {
        String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!checkIsBearer(value)) {
            return;
        }

        String jwt = value.substring(TOKEN_PREFIX.length());
        JwtResult jwtResult = this.jwtProvider.validate(jwt);
        if (jwtResult == JwtResult.VALID) {
            Authentication authentication = this.jwtProvider.resolveToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (jwtResult == JwtResult.EXPIRED) {
            // TODO generate new jwt token
        }
    }

    private boolean checkIsBearer(String jwt) {
        return jwt != null && jwt.startsWith(TOKEN_PREFIX);
    }

}