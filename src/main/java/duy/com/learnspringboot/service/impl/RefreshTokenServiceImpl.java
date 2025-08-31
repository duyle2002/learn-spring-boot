package duy.com.learnspringboot.service.impl;

import duy.com.learnspringboot.entity.RefreshToken;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.repository.RefreshTokenRepository;
import duy.com.learnspringboot.service.IRefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements IRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        revokeAllUserRefreshTokens(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(generateSecureToken())
                .revoked(false)
                .expiresAt(Instant.now().plusSeconds(refreshTokenExpiration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findActiveRefreshTokenByToken(String token) {
        return refreshTokenRepository.findByTokenAndRevokedIsFalse(token);
    }

    @Override
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });
    }

    @Override
    public boolean isTokenValid(RefreshToken refreshToken) {
        return !refreshToken.isRevoked() && refreshToken.getExpiresAt().isBefore(Instant.now());
    }

    @Override
    public void revokeAllUserRefreshTokens(User user) {
        refreshTokenRepository.revokeAllUserRefreshTokens(user);
    }

    private String generateSecureToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
