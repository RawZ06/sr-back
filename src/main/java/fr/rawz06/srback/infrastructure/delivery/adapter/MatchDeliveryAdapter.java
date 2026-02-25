package fr.rawz06.srback.infrastructure.delivery.adapter;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.NullAvailabilityException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.exception.business.SameStaffException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.RestreamerAvailability;
import fr.rawz06.srback.core.port.input.match.AssignStaff;
import fr.rawz06.srback.core.port.input.match.GetPublicMatches;
import fr.rawz06.srback.core.port.input.match.ToggleMatchVisibility;
import fr.rawz06.srback.core.port.input.match.UpdateRestreamerAvailability;
import fr.rawz06.srback.infrastructure.delivery.dto.MatchDTO;
import fr.rawz06.srback.infrastructure.delivery.exception.BadAvailabilitySetException;
import fr.rawz06.srback.infrastructure.delivery.mapper.MapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchDeliveryAdapter {
    private final MapperDTO<MatchDTO, Match> matchMapper;
    private final GetPublicMatches getPublicMatches;
    private final ToggleMatchVisibility toggleMatchVisibility;
    private final AssignStaff assignStaff;
    private final UpdateRestreamerAvailability updateRestreamerAvailability;

    public List<MatchDTO> getPublicMatches() {
        return this.getPublicMatches.getPublicMatches().stream().map(matchMapper::toDTO).toList();
    }

    public MatchDTO updateStaffMatch(long matchId, String host, String cohost) throws MatchNotFoundException, RestreamerNotFoundException, SameStaffException {
        Match match = this.assignStaff.assignStaff(matchId, host, cohost);
        return this.matchMapper.toDTO(match);
    }

    public MatchDTO updateRestreamerAvailability(long matchId, String restreamer, String availability) throws MatchNotFoundException, RestreamerNotFoundException, BadAvailabilitySetException, NullAvailabilityException {
        RestreamerAvailability restreamerAvailability;
        try {
            restreamerAvailability = RestreamerAvailability.valueOf(availability);
        } catch (IllegalArgumentException e) {
            throw new BadAvailabilitySetException(availability);
        }
        Match match = this.updateRestreamerAvailability.updateRestreamerAvailability(matchId, restreamer, restreamerAvailability);
        return this.matchMapper.toDTO(match);
    }

    public MatchDTO toggleMatchVisibility(long matchId) throws MatchNotFoundException {
        Match match = this.toggleMatchVisibility.toggleVisibility(matchId);
        return this.matchMapper.toDTO(match);
    }
}
