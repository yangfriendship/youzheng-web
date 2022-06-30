package me.youzheng.userservice.test;

import me.youzheng.common.security.domain.Role;
import me.youzheng.userservice.user.domain.dto.UserCreateDto;
import me.youzheng.userservice.user.domain.dto.UserResponse;

public class EntityFactory {

    public static UserCreateDto createUserCreateDto() {
        return UserCreateDto.builder()
            .email("youzheng@gmail.com")
            .password("dnwjd123@@")
            .userName("woojung yang")
            .loginId("woojung")
            .build();
    }

    public static UserResponse createUserResponse() {
        return UserResponse.builder()
            .userNo(1)
            .role(Role.USER)
            .email("youzheng@gmail.com")
            .userName("woojung yang")
            .loginId("woojung")
            .build();
    }
}
