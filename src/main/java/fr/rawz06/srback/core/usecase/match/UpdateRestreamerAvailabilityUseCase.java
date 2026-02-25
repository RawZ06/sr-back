package fr.rawz06.srback.core.usecase.match;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.NullAvailabilityException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.model.RestreamerAvailability;
import fr.rawz06.srback.core.port.input.match.UpdateRestreamerAvailability;
import fr.rawz06.srback.core.port.output.match.FindMatchById;
import fr.rawz06.srback.core.port.output.match.FindRestreamerByName;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateRestreamerAvailabilityUseCase implements UpdateRestreamerAvailability {
    private final FindMatchById findMatchById;
    private final PersistMatch persistMatch;
    private final FindRestreamerByName findRestreamer;

    @Override
    public Match updateRestreamerAvailability(long matchId, String playerName, RestreamerAvailability availability) throws MatchNotFoundException, RestreamerNotFoundException, NullAvailabilityException {
        if(availability == null) {
            throw new NullAvailabilityException();
        }
        Optional<Match> match =  findMatchById.findMatchById(matchId);
        if(match.isEmpty()) {
            throw new MatchNotFoundException(matchId);
        }

        Optional<Restreamer> restreamer = findRestreamer.findRestreamerByName(playerName);
        if(restreamer.isEmpty()) {
            throw new RestreamerNotFoundException(playerName);
        }
        match.get().changeAvailability(restreamer.get(), availability);
        return persistMatch.persist(match.get());
    }
}
