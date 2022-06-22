package me.youzheng.userservice.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.youzheng.userservice.exception.UserException;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User fetchUserByLoginId(String loginId) {
        if (!StringUtils.hasLength(loginId)) {
            return null;
        }
        Optional<User> find = this.userRepository.findByLoginId(loginId);
        return find.orElse(null);
    }

    @Override
    public User create(User user) {
        if (this.userRepository.existsByLoginId(user.getLoginId())) {
            throw new UserException("이미 사용중인 아이디입니다.");
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        // TODO 검증 필요
        return this.userRepository.save(user);
    }

}