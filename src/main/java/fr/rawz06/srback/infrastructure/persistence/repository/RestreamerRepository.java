package fr.rawz06.srback.infrastructure.persistence.repository;

import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.infrastructure.persistence.entity.RestreamerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestreamerRepository extends JpaRepository<RestreamerEntity, Long> {
    Optional<Restreamer> findByName(String restreamerName);
}
