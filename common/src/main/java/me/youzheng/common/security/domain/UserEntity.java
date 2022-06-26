package me.youzheng.common.security.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntity {

    private Integer userNo;

    private String loginId;

    private String email;

    private String password;

    private String userName;

    private Role role;

    private boolean lockYn;

}