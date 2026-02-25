package fr.rawz06.srback.core.port.input.match;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.NullAvailabilityException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.RestreamerAvailability;

public interface UpdateRestreamerAvailability {
    Match updateRestreamerAvailability(long matchId, String playerName, RestreamerAvailability availability) throws MatchNotFoundException, RestreamerNotFoundException, NullAvailabilityException;
}
