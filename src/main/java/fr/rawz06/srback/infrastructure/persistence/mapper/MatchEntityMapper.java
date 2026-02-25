package fr.rawz06.srback.infrastructure.persistence.mapper;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.model.RestreamerAvailability;
import fr.rawz06.srback.infrastructure.persistence.entity.MatchEntity;
import fr.rawz06.srback.infrastructure.persistence.entity.RestreamAvailabilityEntity;
import fr.rawz06.srback.infrastructure.persistence.entity.RestreamerEntity;
import fr.rawz06.srback.infrastructure.persistence.entity.RestreamerAvailabilityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MatchEntityMapper implements MapperEntity<Match, MatchEntity> {
    private final MapperEntity<Restreamer, RestreamerEntity> restreamerEntityMapper;
    private final MapperEntity<RestreamerAvailability, RestreamerAvailabilityEnum> streamerAvailabilityEntityMapper;

    @Override
    public Match mapEntityToPojo(MatchEntity matchEntity) {
        Map<Restreamer, RestreamerAvailability>  restreamerAvailabilityMap = new HashMap<>();
        for (RestreamAvailabilityEntity restreamAvailabilityEntity : matchEntity.getRestreamAvailabilityEntities()) {
            Restreamer restreamer = restreamerEntityMapper.mapEntityToPojo(restreamAvailabilityEntity.getRestreamer());
            RestreamerAvailability restreamerAvailability = streamerAvailabilityEntityMapper.mapEntityToPojo(restreamAvailabilityEntity.getAvailability());
            restreamerAvailabilityMap.put(restreamer, restreamerAvailability);
        }
        return new Match(
                matchEntity.getId(),
                matchEntity.getPlayer1(),
                matchEntity.getPlayer2(),
                matchEntity.getDatetime(),
                matchEntity.getRound(),
                matchEntity.getHost() != null ? restreamerEntityMapper.mapEntityToPojo(matchEntity.getHost()) : null,
                matchEntity.getCohost() != null ? restreamerEntityMapper.mapEntityToPojo(matchEntity.getCohost()) : null,
                matchEntity.isVisible(),
                restreamerAvailabilityMap
        );
    }

    @Override
    public MatchEntity mapPojoToEntity(Match match) {
        List<RestreamAvailabilityEntity> restreamAvailabilityEntities = new ArrayList<>();
        for (Map.Entry<Restreamer, RestreamerAvailability> entry : match.getAvailabilities().entrySet()) {
            RestreamAvailabilityEntity restreamAvailabilityEntity = new RestreamAvailabilityEntity();
            restreamAvailabilityEntity.setRestreamer(restreamerEntityMapper.mapPojoToEntity(entry.getKey()));
            restreamAvailabilityEntity.setAvailability(streamerAvailabilityEntityMapper.mapPojoToEntity(entry.getValue()));
            restreamAvailabilityEntities.add(restreamAvailabilityEntity);
        }
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setId(match.getId());
        matchEntity.setPlayer1(match.getPlayer1());
        matchEntity.setPlayer2(match.getPlayer2());
        matchEntity.setDatetime(match.getDatetime());
        matchEntity.setRound(match.getRound());
        matchEntity.setVisible(match.isVisible());
        if(match.getHost() != null) {
            matchEntity.setHost(restreamerEntityMapper.mapPojoToEntity(match.getHost()));
        }
        if(match.getCohost() != null) {
            matchEntity.setCohost(restreamerEntityMapper.mapPojoToEntity(match.getCohost()));
        }
        matchEntity.setRestreamAvailabilityEntities(restreamAvailabilityEntities);
        return matchEntity;
    }
}
