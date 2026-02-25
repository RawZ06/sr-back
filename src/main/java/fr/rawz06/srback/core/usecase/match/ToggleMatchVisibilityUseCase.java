package fr.rawz06.srback.core.usecase.match;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.port.input.match.ToggleMatchVisibility;
import fr.rawz06.srback.core.port.output.match.FindMatchById;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ToggleMatchVisibilityUseCase implements ToggleMatchVisibility {
    private final FindMatchById findMatchById;
    private final PersistMatch persistMatch;

    @Override
    public Match toggleVisibility(long matchId) throws MatchNotFoundException {
        Optional<Match> match = findMatchById.findMatchById(matchId);
        if (match.isEmpty()) {
            throw new MatchNotFoundException(matchId);
        }

        match.get().toggleVisibility();
        return persistMatch.persist(match.get());
    }
}
