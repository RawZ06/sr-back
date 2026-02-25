package fr.rawz06.srback.infrastructure.delivery.rest;

import fr.rawz06.srback.infrastructure.delivery.adapter.TournamentDeliveryAdapter;
import fr.rawz06.srback.infrastructure.delivery.dto.TournamentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentDeliveryAdapter tournamentDeliveryAdapter;

    @GetMapping("")
    public List<TournamentDTO> getTournaments() {
        return tournamentDeliveryAdapter.getTournaments();
    }
}
