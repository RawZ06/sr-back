package fr.rawz06.srback.core.port.input.tournament;

import fr.rawz06.srback.core.model.Tournament;

import java.util.List;

public interface SyncTournaments {
    List<Tournament> syncTournaments();
}
