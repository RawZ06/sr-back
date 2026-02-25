package fr.rawz06.srback.core.port.input.match;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.model.Match;

public interface ToggleMatchVisibility {
    Match toggleVisibility(long matchId) throws MatchNotFoundException;
}
