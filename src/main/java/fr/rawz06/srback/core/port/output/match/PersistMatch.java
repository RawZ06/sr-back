package fr.rawz06.srback.core.port.output.match;

import fr.rawz06.srback.core.model.Match;

public interface PersistMatch {
    Match persist(Match match);
}
