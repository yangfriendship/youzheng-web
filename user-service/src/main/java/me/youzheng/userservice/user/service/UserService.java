package me.youzheng.userservice.user.service;

import java.util.List;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.domain.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User fetchUserByLoginId(String loginId);

    User create(User user);

    Page<List<UserResponse>> fetchUsers(Pageable pageable);
}
