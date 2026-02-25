package fr.rawz06.srback.core.port.output.match;

import fr.rawz06.srback.core.model.Match;

import java.util.Optional;

public interface FindMatchById {
    Optional<Match> findMatchById(long matchId);
}
