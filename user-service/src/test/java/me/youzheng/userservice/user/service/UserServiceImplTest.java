package me.youzheng.userservice.user.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import me.youzheng.common.exception.UserException;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    UserService userService;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        this.userService = new UserServiceImpl(this.userRepository, this.passwordEncoder);
    }

    @Test
    @Description("null 을 넘기면 db 조회하지 않고 바로 null 을 반환")
    public void fetchUserByLoginIdTest_null_parameter() {
        // given
        String loginId = null;
        // when
        User result = this.userService.fetchUserByLoginId(loginId);

        // then
        assertNull(result, "입력한 loginId 가 null Or empty 이기 때문에 바로 null 을 반환해야한다.");
        verify(this.userRepository, times(0)).findByLoginId(anyString());
    }

    @Test
    @Description("존재하는 계정일 경우 null 이 아닌 객체를 리턴")
    public void fetchUserByLoginIdTest_success() {
        // given
        String loginId = "loginId";
        when(this.userRepository.findByLoginId(anyString())).thenReturn(Optional.of(new User()));
        // when
        User result = this.userService.fetchUserByLoginId(loginId);

        // then
        assertNotNull(result, "정상적으로 계정을 조회하여 User 객체를 반환해야한다.");
    }

    @Test
    @Description("존재하는 계정일 경우 null 이 아닌 객체를 리턴")
    public void fetchUserByLoginIdTest_return_null() {
        // given
        String loginId = "loginId";
        when(this.userRepository.findByLoginId(anyString())).thenReturn(Optional.empty());
        // when
        User result = this.userService.fetchUserByLoginId(loginId);

        // then
        assertNull(result, "DB 에서 해당하는 계정을 조회하지 못하여 null 을 반환해야한다.");
    }

    @Test
    @Description("이미 사용중인 loginId")
    public void create_duplicate_loginId() {
        // given
        User user = User.builder()
            .password("rawPassword")
            .loginId("loginId")
            .build();
        when(this.userRepository.existsByLoginId(any())).thenReturn(true);

        // when
        assertThrows(UserException.class, () -> {
            this.userService.create(user);
        }, "이미 사용중인 loginId 라서 UserException 예외를 발생시킨다.");
        verify(this.userRepository, times(0)).save(any(User.class));
    }

    @Test
    @Description("User Save")
    public void create_success() {
        // given
        final String expected = "encodedPassword";
        User user = User.builder()
            .password("rawPassword")
            .loginId("loginId")
            .build();
        when(this.userRepository.existsByLoginId(any())).thenReturn(false);
        when(this.passwordEncoder.encode(user.getPassword())).thenReturn(expected);

        // when
        User result = this.userService.create(user);

        // then
        verify(this.passwordEncoder, times(1)).encode(anyString());
        verify(this.userRepository, times(1)).save(any(User.class));
    }

}