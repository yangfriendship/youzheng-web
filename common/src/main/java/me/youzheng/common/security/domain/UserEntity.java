package me.youzheng.common.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Integer userNo;

    private String loginId;

    private String email;

    private String password;

    private String userName;

    private Role role;

    private boolean lockYn;

    public boolean isAuthenticated() {
        return true;
    }

    public boolean canLogin() {
        return this.lockYn;
    }

}