package fr.rawz06.srback.infrastructure.delivery.mapper;

import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.model.RestreamerAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AvailabilitiesDTOMapper implements MapperDTO<Map<String, String>, Map<Restreamer, RestreamerAvailability>> {

    @Override
    public Map<String, String> toDTO(Map<Restreamer, RestreamerAvailability> entity) {
        return entity.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().toString()
                ));
    }

    @Override
    public Map<Restreamer, RestreamerAvailability> toPOJO(Map<String, String> stringStringMap) {
        return stringStringMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new Restreamer(entry.getKey()),
                        entry -> RestreamerAvailability.valueOf(entry.getValue())
                ));
    }
}
