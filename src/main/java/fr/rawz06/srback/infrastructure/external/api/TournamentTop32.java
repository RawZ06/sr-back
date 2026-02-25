package fr.rawz06.srback.infrastructure.external.api;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.core.port.output.tournament.TournamentProviderPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TournamentTop32 implements TournamentProviderPort {

    @Override
    public Tournament getTournament() {
        return new Tournament("Top 32", List.of(
                new Match("Marco", "Rafa", LocalDateTime.now(), "Round 1"),
                new Match("Icola", "Naestriel", LocalDateTime.now(), "Round 1"),
                new Match("RyuuKane", "Exodus", LocalDateTime.now(), "Round 2"),
                new Match("Drooness", "Cola", LocalDateTime.now(), "Round 2"),
                new Match("Volc", "rockchalk", LocalDateTime.now(), "Round 2")
        ));
    }
}
