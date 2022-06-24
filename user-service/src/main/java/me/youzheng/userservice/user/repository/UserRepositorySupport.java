package me.youzheng.userservice.user.repository;

import java.util.List;
import me.youzheng.userservice.user.domain.dto.UserRequest;
import me.youzheng.userservice.user.domain.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositorySupport {

    List<UserResponse> findUsers(UserRequest userRequest, Pageable pageable);
}
