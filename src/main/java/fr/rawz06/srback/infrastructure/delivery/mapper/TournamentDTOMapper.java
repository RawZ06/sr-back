package fr.rawz06.srback.infrastructure.delivery.mapper;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.infrastructure.delivery.dto.MatchDTO;
import fr.rawz06.srback.infrastructure.delivery.dto.TournamentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TournamentDTOMapper implements MapperDTO<TournamentDTO, Tournament> {
    private final MapperDTO<MatchDTO, Match> matchDTOMapper;

    @Override
    public TournamentDTO toDTO(Tournament entity) {
        return new TournamentDTO(
                entity.getId(),
                entity.getName(),
                entity.getMatches().stream().map(matchDTOMapper::toDTO).toList()
        );
    }

    @Override
    public Tournament toPOJO(TournamentDTO tournamentDTO) {
        return new Tournament(
                tournamentDTO.getId(),
                tournamentDTO.getName(),
                tournamentDTO.getMatches().stream().map(matchDTOMapper::toPOJO).toList()
        );
    }
}
