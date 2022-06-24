package me.youzheng.userservice.user.controller;

import com.sun.tools.javac.util.List;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.domain.dto.UserCreateRequest;
import me.youzheng.userservice.user.domain.dto.UserResponse;
import me.youzheng.userservice.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest createRequest) {
        User created = this.userService.create(createRequest.convertToUser());
        log.info("새로운 계정 생성: userNo: {}, loginId: {}, role: {}", created.getUserNo(),
            created.getLoginId(), created.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(created));
    }

    @GetMapping("/api/users")
    public ResponseEntity<Page<List<UserResponse>>> fetchUsers(@PageableDefault(sort = {"userNo"}) Pageable pageable) {
        Page<List<UserResponse>> users = this.userService.fetchUsers(pageable);
        return ResponseEntity.ok(users);
    }

}