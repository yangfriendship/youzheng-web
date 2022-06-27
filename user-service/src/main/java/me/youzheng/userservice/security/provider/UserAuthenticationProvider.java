package me.youzheng.userservice.security.provider;

import lombok.RequiredArgsConstructor;
import me.youzheng.common.security.domain.UserContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String loginId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginId);
        if (!(userDetails instanceof UserContext)) {
            throw new BadCredentialsException("[ERROR] 로그인 실패, 존재하지 않는 계정");
        }

        UserContext userContext = (UserContext) userDetails;
        if (!this.passwordEncoder.matches(password, userContext.getPassword())) {
            throw new BadCredentialsException("[ERROR] 로그인 실패, BadCredential");
        }
        if (!userContext.canLogin()) {
            throw new BadCredentialsException("[ERROR] 로그인 실패, 현재 로그인할 수 없는 계정입니다.");
        }

        userContext.afterAuthenticate(); // clear password
        return new UsernamePasswordAuthenticationToken(userContext, userContext.getPassword(),
            userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}