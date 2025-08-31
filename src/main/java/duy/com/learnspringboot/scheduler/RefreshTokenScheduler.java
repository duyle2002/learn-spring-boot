package duy.com.learnspringboot.scheduler;

import duy.com.learnspringboot.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class RefreshTokenScheduler {
    RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "${cron-schedule-delete-expired-refresh-tokens}")
    public void deleteExpiredRefreshTokens() {
        log.info("Deleting expired refresh tokens");
        refreshTokenRepository.deleteExpiredRefreshTokens(Instant.now());
    }
}
