package fr.rawz06.srback.core.usecase.tournament;

import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.core.port.input.tournament.GetAllTournaments;
import fr.rawz06.srback.core.port.output.tournament.FindTournaments;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllTournamentsUseCase implements GetAllTournaments {
    private final FindTournaments findTournaments;

    @Override
    public List<Tournament> findTournaments() {
        return findTournaments.findTournaments();
    }
}
