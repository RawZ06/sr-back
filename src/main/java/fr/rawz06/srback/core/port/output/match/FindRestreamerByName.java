package fr.rawz06.srback.core.port.output.match;

import fr.rawz06.srback.core.model.Restreamer;

import java.util.Optional;

public interface FindRestreamerByName {
    Optional<Restreamer> findRestreamerByName(String restreamerName);
}
