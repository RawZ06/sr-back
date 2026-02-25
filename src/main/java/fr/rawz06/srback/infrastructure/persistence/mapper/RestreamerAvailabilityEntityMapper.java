package fr.rawz06.srback.infrastructure.persistence.mapper;

import fr.rawz06.srback.core.model.RestreamerAvailability;
import fr.rawz06.srback.infrastructure.persistence.entity.RestreamerAvailabilityEnum;
import org.springframework.stereotype.Component;

@Component
public class RestreamerAvailabilityEntityMapper implements MapperEntity<RestreamerAvailability, RestreamerAvailabilityEnum>{
    @Override
    public RestreamerAvailability mapEntityToPojo(RestreamerAvailabilityEnum restreamerAvailabilityEnum) {
        return RestreamerAvailability.valueOf(restreamerAvailabilityEnum.name());
    }

    @Override
    public RestreamerAvailabilityEnum mapPojoToEntity(RestreamerAvailability restreamerAvailability) {
        return RestreamerAvailabilityEnum.valueOf(restreamerAvailability.name());
    }
}
