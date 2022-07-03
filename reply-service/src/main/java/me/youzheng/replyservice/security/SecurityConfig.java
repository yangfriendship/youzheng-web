package me.youzheng.replyservice.security;

import lombok.RequiredArgsConstructor;
import me.youzheng.common.security.filter.TokenAuthenticationFilter;
import me.youzheng.common.security.util.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.GenericFilterBean;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CharacterEncodingFilter characterEncodingFilter;
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expired-seconds}")
    private long expiredSeconds;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
            .anyRequest().authenticated()
        ;

        http.addFilterBefore(this.characterEncodingFilter, CsrfFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin()
            .disable()
            .httpBasic()
            .disable()
            .cors()
            .disable()
            .csrf()
            .disable()
        ;
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(this.secretKey, this.expiredSeconds);
    }

    @Bean
    public GenericFilterBean tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(this.jwtProvider());
    }

}
