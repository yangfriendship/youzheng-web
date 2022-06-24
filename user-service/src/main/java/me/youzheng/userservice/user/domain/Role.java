package me.youzheng.userservice.user.domain;

import java.util.Arrays;

public enum Role {

    USER, ADMIN;

    public static Role of(String role) {
        return Arrays.stream(values()).filter(r -> r.name().equals(role))
            .findFirst()
            .orElse(null);
    }
}
