package fr.rawz06.srback.infrastructure.persistence.adapter;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.port.output.match.FindMatchById;
import fr.rawz06.srback.core.port.output.match.FindMatchesByVisibility;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import fr.rawz06.srback.infrastructure.persistence.entity.MatchEntity;
import fr.rawz06.srback.infrastructure.persistence.mapper.MatchEntityMapper;
import fr.rawz06.srback.infrastructure.persistence.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatchPersistenceAdapter implements FindMatchesByVisibility, FindMatchById, PersistMatch {
    private final MatchRepository matchRepository;
    private final MatchEntityMapper matchEntityMapper;

    @Override
    public Optional<Match> findMatchById(long matchId) {
        return matchRepository.findById(matchId)
                .map(matchEntityMapper::mapEntityToPojo);
    }

    @Override
    public List<Match> findMatchesByVisibility(boolean visibility) {
        return matchRepository.findByVisible(visibility)
                .stream()
                .map(matchEntityMapper::mapEntityToPojo)
                .toList();
    }

    @Override
    public Match persist(Match match) {
        return matchEntityMapper.mapEntityToPojo(matchRepository.save(matchEntityMapper.mapPojoToEntity(match)));
    }
}
