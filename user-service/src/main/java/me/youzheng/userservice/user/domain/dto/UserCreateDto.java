package me.youzheng.userservice.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.youzheng.userservice.user.domain.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private String loginId;

    private String email;

    private String password;

    private String userName;

    public User convertToUser() {
        return User.builder()
            .userName(this.userName)
            .loginId(this.loginId)
            .password(this.password)
            .email(this.email)
            .build();
    }

}