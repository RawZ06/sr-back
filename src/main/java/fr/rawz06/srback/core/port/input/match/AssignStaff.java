package fr.rawz06.srback.core.port.input.match;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.exception.business.SameStaffException;
import fr.rawz06.srback.core.model.Match;

public interface AssignStaff {
    Match assignStaff(long matchId, String host, String cohost) throws MatchNotFoundException, RestreamerNotFoundException, SameStaffException;
}
