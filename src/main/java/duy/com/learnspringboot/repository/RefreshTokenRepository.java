package duy.com.learnspringboot.repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import duy.com.learnspringboot.entity.RefreshToken;
import duy.com.learnspringboot.entity.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenAndRevokedIsFalse(String token);

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE from RefreshToken r where r.expiresAt < :now")
    void deleteExpiredRefreshTokens(@Param("now") Instant now);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true where r.user = :user")
    void revokeAllUserRefreshTokens(@Param("user") User user);
}
