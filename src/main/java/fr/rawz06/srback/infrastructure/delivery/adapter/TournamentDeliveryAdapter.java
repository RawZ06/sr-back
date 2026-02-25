package fr.rawz06.srback.infrastructure.delivery.adapter;

import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.core.port.output.tournament.FindTournaments;
import fr.rawz06.srback.infrastructure.delivery.dto.TournamentDTO;
import fr.rawz06.srback.infrastructure.delivery.mapper.MapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TournamentDeliveryAdapter {
    private final FindTournaments findTournaments;
    private final MapperDTO<TournamentDTO, Tournament> tournamentMapper;

    public List<TournamentDTO> getTournaments() {
        return findTournaments.findTournaments().stream()
                .map(tournamentMapper::toDTO)
                .toList();
    }
}
