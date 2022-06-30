package me.youzheng.userservice.user.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.common.exception.UserException;
import me.youzheng.common.security.annotation.LoginUser;
import me.youzheng.common.security.domain.UserEntity;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.domain.dto.UserCreateDto;
import me.youzheng.userservice.user.domain.dto.UserResponse;
import me.youzheng.userservice.user.service.UserService;
import me.youzheng.userservice.user.validator.UserCreateValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String URL_PREFIX = "/api/users";

    private final UserService userService;
    private final SecurityUtil securityUtil;

    @InitBinder
    public void initUserCreateDtoBinding(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new UserCreateValidator());
    }

    @PostMapping(URL_PREFIX)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateDto createRequest,
        Errors error) {
        if (error.hasErrors()) {
            throw new UserException(error);
        }

        User created = this.userService.create(createRequest.convertToUser());
        log.info("새로운 계정 생성: userNo: {}, loginId: {}, role: {}", created.getUserNo(),
            created.getLoginId(), created.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(created));
    }

    @GetMapping(URL_PREFIX)
    public ResponseEntity<Page<List<UserResponse>>> fetchUsers(
        @PageableDefault(sort = {"userNo"}) Pageable pageable) {
        Page<List<UserResponse>> users = this.userService.fetchUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(URL_PREFIX + "/{userNo}")
    public ResponseEntity<UserResponse> fetchDetailUser(@PathVariable("userNo") Integer userNo) {
        if (!this.securityUtil.isOwner(userNo)) {
            throw new UserException("can't access!!", 403);
        }
        return ResponseEntity.ok(this.userService.fetchByUserNo(userNo));
    }

}