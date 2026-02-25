package fr.rawz06.srback.core.usecase.match;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.SameStaffException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.port.input.match.AssignStaff;
import fr.rawz06.srback.core.port.output.match.FindMatchById;
import fr.rawz06.srback.core.port.output.match.FindRestreamerByName;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AssignStaffUseCase implements AssignStaff {
    private final FindMatchById findMatchById;
    private final FindRestreamerByName findRestreamerByName;
    private final PersistMatch persistMatch;

    @Override
    public Match assignStaff(long matchId, String hostName, String cohostName) throws MatchNotFoundException, SameStaffException {
        Optional<Match> match = findMatchById.findMatchById(matchId);
        if (match.isEmpty()) {
            throw new MatchNotFoundException(matchId);
        }
        Restreamer host = findRestreamerByName(hostName);
        Restreamer cohost = findRestreamerByName(cohostName);
        Match updatedMatch = match.get();

        if(host != null && host.equals(cohost)) {
            throw new SameStaffException();
        }

        updatedMatch.assignStaff(host, cohost);
        return persistMatch.persist(updatedMatch);
    }

    private Restreamer findRestreamerByName(String name) {
        Optional<Restreamer> restreamer = findRestreamerByName.findRestreamerByName(name);
        return restreamer.orElse(null);
    }
}
