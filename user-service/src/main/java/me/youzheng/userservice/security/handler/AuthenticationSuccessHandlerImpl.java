package me.youzheng.userservice.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.youzheng.common.security.util.JwtProvider;
import me.youzheng.common.security.util.SecurityUtil;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final SecurityUtil securityUtil;
    public static final String TOKEN_KEY_NAME = "token";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        response.setStatus(200);
        String token = this.jwtProvider.createToken(
            SecurityContextHolder.getContext().getAuthentication());

        Map<String, Object> body = new HashMap<>();
        body.put(TOKEN_KEY_NAME, token);
        body.put("user", this.securityUtil.getCurrentUser().getUserEntity());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Encoding.DEFAULT_CHARSET.name());
        response.getWriter().write(this.objectMapper.writeValueAsString(body));
    }

}