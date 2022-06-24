package me.youzheng.userservice.refreshtoken.repository;

import java.util.Optional;
import me.youzheng.userservice.refreshtoken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findFirstByUserUserNo(Integer userNo);

}