package fr.rawz06.srback.infrastructure.configuration;

import fr.rawz06.srback.core.port.input.match.AssignStaff;
import fr.rawz06.srback.core.port.input.match.GetPublicMatches;
import fr.rawz06.srback.core.port.input.match.ToggleMatchVisibility;
import fr.rawz06.srback.core.port.input.match.UpdateRestreamerAvailability;
import fr.rawz06.srback.core.port.output.match.FindMatchById;
import fr.rawz06.srback.core.port.output.match.FindMatchesByVisibility;
import fr.rawz06.srback.core.port.output.match.FindRestreamerByName;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import fr.rawz06.srback.core.usecase.match.AssignStaffUseCase;
import fr.rawz06.srback.core.usecase.match.GetPublicMatchesUseCase;
import fr.rawz06.srback.core.usecase.match.ToggleMatchVisibilityUseCase;
import fr.rawz06.srback.core.usecase.match.UpdateRestreamerAvailabilityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatchConfig {
    @Bean
    public AssignStaff assignStaff(
            FindMatchById findMatchById,
            FindRestreamerByName findRestreamerByName,
            PersistMatch persistMatch
    ) {
        return new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);
    }

    @Bean
    public GetPublicMatches getPublicMatches(FindMatchesByVisibility findMatchesByVisibility) {
        return new GetPublicMatchesUseCase(findMatchesByVisibility);
    }

    @Bean
    public ToggleMatchVisibility toggleMatchVisibility(
            FindMatchById findMatchById,
            PersistMatch persistMatch) {
        return new ToggleMatchVisibilityUseCase(findMatchById, persistMatch);
    }

    @Bean
    public UpdateRestreamerAvailability updateRestreamerAvailability(
            FindMatchById findMatchById,
            PersistMatch persistMatch,
            FindRestreamerByName findRestreamerByName) {
        return new UpdateRestreamerAvailabilityUseCase(findMatchById, persistMatch, findRestreamerByName);
    }
}
