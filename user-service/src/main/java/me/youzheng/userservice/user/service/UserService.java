package me.youzheng.userservice.user.service;

import me.youzheng.userservice.user.domain.User;

public interface UserService {

    User fetchUserByLoginId(String loginId);

    User create(User user);
}
