package me.youzheng.userservice.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.youzheng.common.security.util.JwtProvider;
import me.youzheng.common.security.filter.TokenAuthenticationFilter;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.common.security.util.SecurityUtilImpl;
import me.youzheng.userservice.security.filter.TokenLoginProcessorFilter;
import me.youzheng.userservice.security.handler.AccessDeniedHandlerImpl;
import me.youzheng.userservice.security.handler.AuthenticationEntryPointImpl;
import me.youzheng.userservice.security.handler.AuthenticationFailureHandlerImpl;
import me.youzheng.userservice.security.handler.AuthenticationSuccessHandlerImpl;
import me.youzheng.userservice.security.provider.UserAuthenticationProvider;
import me.youzheng.userservice.security.service.UserDetailsServiceImpl;
import me.youzheng.userservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SIGN_IN_URL = "/api/users";
    public static final String LOGIN_URL = "/api/login";

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expired-seconds}")
    private long expiredSeconds;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final CharacterEncodingFilter characterEncodingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, SIGN_IN_URL).permitAll()
            .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
            .antMatchers("/api/**").authenticated()
        ;

        http.exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPoint())
            .accessDeniedHandler(this.accessDeniedHandler());

        http.addFilterBefore(this.characterEncodingFilter, CsrfFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenLoginProcessorFilter(),
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
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> jwtConfigAdaptor()
        throws Exception {
        return new JwtConfigAdaptor(
            this.accessDeniedHandler(),
            this.tokenAuthenticationFilter(),
            this.tokenLoginProcessorFilter()
        );
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(this.secretKey, this.expiredSeconds);
    }

    /**
     * 인증 실패시 처리 핸들러 (401)
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 인증 성공 처리 핸들러
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl(this.objectMapper, jwtProvider(),
            securityUtil());
    }

    /**
     * 권한 거부에 대한 처리 핸들러 (403)
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl(this.objectMapper);
    }

    /**
     * 인증에 사용되는 계정 조회
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(this.userService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new UserAuthenticationProvider(userDetailsService(), this.passwordEncoder);
    }

    @Bean
    public GenericFilterBean tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(this.jwtProvider());
    }

    @Bean
    public GenericFilterBean tokenLoginProcessorFilter() throws Exception {
        TokenLoginProcessorFilter processorFilter = new TokenLoginProcessorFilter(
            LOGIN_URL, this.objectMapper);
        processorFilter.setAuthenticationManager(this.authenticationManagerBean());
        processorFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler());
        processorFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler());
        return processorFilter;
    }

    @Bean
    public SecurityUtil securityUtil() {
        return new SecurityUtilImpl();
    }

}