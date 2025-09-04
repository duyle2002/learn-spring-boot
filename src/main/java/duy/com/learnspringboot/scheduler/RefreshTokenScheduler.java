package duy.com.learnspringboot.scheduler;

import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import duy.com.learnspringboot.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;

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
