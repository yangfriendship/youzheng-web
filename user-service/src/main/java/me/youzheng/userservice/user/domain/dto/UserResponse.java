package me.youzheng.userservice.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.youzheng.common.security.domain.Role;
import me.youzheng.userservice.user.domain.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer userNo;

    private String loginId;

    private String email;

    private String userName;

    private Role role;


    public static UserResponse from(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
            .userNo(user.getUserNo())
            .loginId(user.getLoginId())
            .email(user.getEmail())
            .userName(user.getUserName())
            .role(user.getRole())
            .build();
    }
}
