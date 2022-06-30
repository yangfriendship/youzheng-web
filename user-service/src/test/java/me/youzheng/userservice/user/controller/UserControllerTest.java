package me.youzheng.userservice.user.controller;


import static me.youzheng.userservice.user.controller.UserController.URL_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import me.youzheng.common.security.domain.Role;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.userservice.exception.ApiExceptionHandler;
import me.youzheng.userservice.test.EntityFactory;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.domain.dto.UserCreateDto;
import me.youzheng.userservice.user.domain.dto.UserResponse;
import me.youzheng.userservice.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    MockMvc mockMvc;
    UserController userController;
    @Mock
    UserService userService;
    @Mock
    SecurityUtil securityUtil;
    // stub
    UserCreateDto createDto;

    ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        this.createDto = UserCreateDto.builder()
            .loginId("loginId")
            .userName("woojung")
            .password("dnwjd123@@##")
            .email("yangfriendship@naver.com")
            .build();
        this.objectMapper = new ObjectMapper();
        this.userController = new UserController(this.userService, this.securityUtil);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.userController)
            .setControllerAdvice(new ApiExceptionHandler())
            .build();
    }

    @Test
    public void createUserTest() throws Exception {
        // given
        when(this.userService.create(any(User.class))).thenReturn(User.builder()
            .userNo(1)
            .loginId("loginId")
            .userName("woojung")
            .password("dnwjd123@@##")
            .role(Role.USER)
            .email("yangfriendship@naver.com")
            .createDatetime(LocalDateTime.now())
            .build());
        // when
        this.mockMvc.perform(post(URL_PREFIX)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(this.createDto))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.userNo").exists())
            .andExpect(jsonPath("$.userName").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.role").exists())
            .andExpect(jsonPath("$.password").doesNotExist())
        ;
    }

    @Test
    public void fetchDetailUserTest_forbidden() throws Exception {

        when(this.securityUtil.isOwner(any())).thenReturn(false);

        this.mockMvc.perform(get(URL_PREFIX + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(this.createDto))
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").exists())
            .andDo(print());
    }

    @Test
    public void fetchDetailUserTest_success() throws Exception {
        // given
        UserResponse userResponse = EntityFactory.createUserResponse();
        when(this.securityUtil.isOwner(any())).thenReturn(true);
        when(this.userService.fetchByUserNo(anyInt())).thenReturn(
            userResponse);

        // when
        this.mockMvc.perform(get(URL_PREFIX + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(this.createDto))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userName").exists())
            .andExpect(jsonPath("$.userNo").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.role").exists())
            .andDo(print());
    }

}