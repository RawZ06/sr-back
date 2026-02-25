package fr.rawz06.srback.infrastructure.persistence.repository;

import fr.rawz06.srback.infrastructure.persistence.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {
}
