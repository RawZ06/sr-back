package fr.rawz06.srback.infrastructure.persistence.adapter;

import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.port.output.match.FindRestreamerByName;
import fr.rawz06.srback.infrastructure.persistence.repository.RestreamerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestreamerPersistenceAdapter implements FindRestreamerByName {
    private final RestreamerRepository restreamerRepository;

    @Override
    public Optional<Restreamer> findRestreamerByName(String restreamerName) {
        return restreamerRepository.findByName(restreamerName);
    }
}
