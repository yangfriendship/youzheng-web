package me.youzheng.userservice.security.service;

import lombok.RequiredArgsConstructor;
import me.youzheng.userservice.security.domain.UserContext;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        User user = this.userService.fetchUserByLoginId(loginId);
        if (user == null) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
        }

        return new UserContext(user);
    }
}
