package fr.rawz06.srback.core.usecase.match;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.port.input.match.GetPublicMatches;
import fr.rawz06.srback.core.port.output.match.FindMatchesByVisibility;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetPublicMatchesUseCase implements GetPublicMatches {
    private final FindMatchesByVisibility findMatchesByVisibility;

    @Override
    public List<Match> getPublicMatches() {
        return findMatchesByVisibility.findMatchesByVisibility(true);
    }
}
