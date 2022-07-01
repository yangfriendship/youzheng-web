package me.youzheng.userservice.security.service;

import lombok.RequiredArgsConstructor;
import me.youzheng.common.security.domain.UserContext;
import me.youzheng.common.security.domain.UserEntity;
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

        UserEntity userEntity = UserEntity.builder()
            .email(user.getEmail())
            .userNo(user.getUserNo())
            .loginId(user.getLoginId())
            .lockYn(user.isLockYn())
            .userName(user.getUserName())
            .password(user.getPassword())
            .role(user.getRole())
            .build();
        return new UserContext(userEntity);
    }
}
