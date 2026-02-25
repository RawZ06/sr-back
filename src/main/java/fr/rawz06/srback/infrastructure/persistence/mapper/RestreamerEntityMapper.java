package fr.rawz06.srback.infrastructure.persistence.mapper;

import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.infrastructure.persistence.entity.RestreamerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestreamerEntityMapper implements MapperEntity<Restreamer, RestreamerEntity> {
    @Override
    public Restreamer mapEntityToPojo(RestreamerEntity restreamerEntity) {
        return new Restreamer(
                restreamerEntity.getId(),
                restreamerEntity.getName()
        );
    }

    @Override
    public RestreamerEntity mapPojoToEntity(Restreamer restreamer) {
        return new RestreamerEntity(
                restreamer.id(),
                restreamer.name()
        );
    }
}
