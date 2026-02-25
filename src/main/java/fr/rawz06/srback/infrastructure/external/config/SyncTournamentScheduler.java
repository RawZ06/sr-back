package fr.rawz06.srback.infrastructure.external.config;

import fr.rawz06.srback.core.port.input.tournament.SyncTournaments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SyncTournamentScheduler {
    private final SyncTournaments syncTournaments;

    @Bean
    public ApplicationRunner initializeSync() {
        return args -> {
            log.info("Initial sync on startup...");
            syncTournaments.syncTournaments();
        };
    }

    @Scheduled(fixedRate = 300000)
    public void executeSync() {
        log.info("Sync tournament scheduled");
        syncTournaments.syncTournaments();
    }
}
