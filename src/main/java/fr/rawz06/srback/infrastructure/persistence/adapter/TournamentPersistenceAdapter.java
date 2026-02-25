package fr.rawz06.srback.infrastructure.persistence.adapter;

import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.core.port.output.tournament.FindTournaments;
import fr.rawz06.srback.core.port.output.tournament.PersistTournament;
import fr.rawz06.srback.infrastructure.persistence.entity.MatchEntity;
import fr.rawz06.srback.infrastructure.persistence.entity.TournamentEntity;
import fr.rawz06.srback.infrastructure.persistence.mapper.MapperEntity;
import fr.rawz06.srback.infrastructure.persistence.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TournamentPersistenceAdapter implements FindTournaments, PersistTournament {
    private final TournamentRepository tournamentRepository;
    private final MapperEntity<Tournament, TournamentEntity> tournamentEntityMapper;

    @Override
    @Transactional
    public List<Tournament> findTournaments() {
        return tournamentRepository.findAll().stream().map(tournamentEntityMapper::mapEntityToPojo).toList();
    }

    @Override
    public Tournament persist(Tournament tournament) {
        return tournamentEntityMapper.mapEntityToPojo(tournamentRepository.save(tournamentEntityMapper.mapPojoToEntity(tournament)));
    }
}
