package duy.com.learnspringboot.service;

import java.util.Optional;

import duy.com.learnspringboot.entity.RefreshToken;
import duy.com.learnspringboot.entity.User;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken> findActiveRefreshTokenByToken(String token);

    void revokeRefreshToken(String token);

    boolean isTokenValid(RefreshToken refreshToken);

    void revokeAllUserRefreshTokens(User user);
}
