package fr.rawz06.srback.core.port.output.match;

import fr.rawz06.srback.core.model.Match;

import java.util.List;

public interface FindMatchesByVisibility {
    List<Match> findMatchesByVisibility(boolean visibility);
}
