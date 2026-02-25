package fr.rawz06.srback.infrastructure.delivery.mapper;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.model.RestreamerAvailability;
import fr.rawz06.srback.infrastructure.delivery.dto.MatchDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MatchDTOMapper implements MapperDTO<MatchDTO, Match> {
    private final MapperDTO<Map<String, String>, Map<Restreamer, RestreamerAvailability>> availabilitiesDTOMapper;

    @Override
    public MatchDTO toDTO(Match entity) {
        return new MatchDTO(
                entity.getId(),
                entity.getPlayer1(),
                entity.getPlayer2(),
                entity.getDatetime(),
                entity.getRound(),
                entity.getHost() != null ? entity.getHost().name() : null,
                entity.getCohost() != null ? entity.getCohost().name() : null,
                entity.isVisible(),
                availabilitiesDTOMapper.toDTO(entity.getAvailabilities())
        );
    }

    @Override
    public Match toPOJO(MatchDTO matchDTO) {
        return new Match(
                matchDTO.getId(),
                matchDTO.getPlayer1(),
                matchDTO.getPlayer2(),
                matchDTO.getDatetime(),
                matchDTO.getRound(),
                Strings.isBlank(matchDTO.getHost()) ? new Restreamer(matchDTO.getHost()) : null,
                Strings.isBlank(matchDTO.getCohost()) ? new Restreamer(matchDTO.getCohost()) : null,
                matchDTO.isVisible(),
                availabilitiesDTOMapper.toPOJO(matchDTO.getAvailabilities())
        );
    }
}
