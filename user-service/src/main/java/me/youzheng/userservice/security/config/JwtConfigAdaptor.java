package me.youzheng.userservice.security.config;

import static me.youzheng.userservice.security.config.SecurityConfig.LOGIN_URL;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtConfigAdaptor extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AccessDeniedHandler accessDeniedHandler;
    private final GenericFilterBean tokenAuthenticationFilter;
    private final GenericFilterBean tokenLoginProcessorFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll();

        http.exceptionHandling()
            .accessDeniedHandler(this.accessDeniedHandler);

        http.addFilterBefore(this.tokenAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.tokenLoginProcessorFilter,
            UsernamePasswordAuthenticationFilter.class);
    }


}