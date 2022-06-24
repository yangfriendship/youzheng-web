package me.youzheng.userservice.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private String defaultMessage;

    public AuthenticationEntryPointImpl() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> message = new HashMap<>();
        message.put("message", "인증이 필요합니다.");
        try {
            this.defaultMessage = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            this.defaultMessage = "{\n"
                + "    \"message\": \"인증이 필요합니다.\"\n"
                + "}";
            e.printStackTrace();
        }
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(defaultMessage);
    }

}