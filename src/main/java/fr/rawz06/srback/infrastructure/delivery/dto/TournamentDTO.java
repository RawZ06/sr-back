package fr.rawz06.srback.infrastructure.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO {
    private Long id;
    private String name;
    private List<MatchDTO> matches = new ArrayList<>();
}
