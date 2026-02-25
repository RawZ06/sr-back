package fr.rawz06.srback.infrastructure.persistence.mapper;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.infrastructure.persistence.entity.MatchEntity;
import fr.rawz06.srback.infrastructure.persistence.entity.TournamentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TournamentEntityMapper implements MapperEntity<Tournament, TournamentEntity> {

    private final MapperEntity<Match, MatchEntity> matchEntityMapperEntity;

    @Override
    public Tournament mapEntityToPojo(TournamentEntity tournamentEntity) {
        return new  Tournament(
                tournamentEntity.getId(),
                tournamentEntity.getName(),
                tournamentEntity.getMatches().stream().map(matchEntityMapperEntity::mapEntityToPojo).toList()
        );
    }

    @Override
    public TournamentEntity mapPojoToEntity(Tournament tournament) {
        return new TournamentEntity(
                tournament.getId(),
                tournament.getName(),
                tournament.getMatches().stream().map(matchEntityMapperEntity::mapPojoToEntity).toList()
        );
    }
}
