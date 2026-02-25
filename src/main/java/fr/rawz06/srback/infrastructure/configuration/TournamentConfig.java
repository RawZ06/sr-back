package fr.rawz06.srback.infrastructure.configuration;

import fr.rawz06.srback.core.port.input.tournament.SyncTournaments;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import fr.rawz06.srback.core.port.output.tournament.FindTournaments;
import fr.rawz06.srback.core.port.output.tournament.PersistTournament;
import fr.rawz06.srback.core.port.output.tournament.TournamentProviderPort;
import fr.rawz06.srback.core.usecase.tournament.SyncTournamentsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TournamentConfig {

    @Bean
    public SyncTournaments syncTournaments(
            List<TournamentProviderPort> tournamentProviders,
            FindTournaments findTournaments,
            PersistTournament persistTournament
    ) {
        return new SyncTournamentsUseCase(tournamentProviders, findTournaments, persistTournament);
    }
}
