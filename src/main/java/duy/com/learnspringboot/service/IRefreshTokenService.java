package duy.com.learnspringboot.service;

import duy.com.learnspringboot.entity.RefreshToken;
import duy.com.learnspringboot.entity.User;

import java.util.Optional;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(User user);
    Optional<RefreshToken> findActiveRefreshTokenByToken(String token);
    void revokeRefreshToken(String token);
    boolean isTokenValid(RefreshToken refreshToken);
    void revokeAllUserRefreshTokens(User user);
}
