package me.youzheng.userservice.user.repository;

import java.util.Optional;
import me.youzheng.userservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
