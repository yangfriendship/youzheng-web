package me.youzheng.userservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class TokenLoginProcessorFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public TokenLoginProcessorFilter(String url, ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(url));
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException, IOException {
        BufferedReader reader = request.getReader();
        HashMap form = this.objectMapper.readValue(reader, HashMap.class);

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(form.get("loginId"), form.get("password")));
    }
}
