package me.youzheng.userservice.user.domain.dto;

import lombok.Data;
import me.youzheng.common.security.domain.Role;

@Data
public class UserRequest {

    private Integer userNo;

    private String loginId;

    private String email;

    private Role role;

    private String userName;

}