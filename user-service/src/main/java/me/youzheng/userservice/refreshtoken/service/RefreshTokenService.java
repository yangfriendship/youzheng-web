package me.youzheng.userservice.refreshtoken.service;

import me.youzheng.userservice.refreshtoken.domain.RefreshToken;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RefreshTokenService {

    @Transactional(readOnly = true)
    RefreshToken fetchByUserNo(Integer userNo);

}