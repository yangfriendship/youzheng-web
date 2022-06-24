package me.youzheng.userservice.refreshtoken.service;

import lombok.RequiredArgsConstructor;
import me.youzheng.userservice.refreshtoken.domain.RefreshToken;
import me.youzheng.userservice.refreshtoken.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken fetchByUserNo(Integer userNo) {
        return this.refreshTokenRepository.findFirstByUserUserNo(userNo).orElse(null);
    }

}