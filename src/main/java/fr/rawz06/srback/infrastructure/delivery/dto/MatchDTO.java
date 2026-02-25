package fr.rawz06.srback.infrastructure.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private Long id;
    private String player1;
    private String player2;
    private LocalDateTime datetime;
    private String round;
    private String host;
    private String cohost;
    private boolean visible;
    private Map<String, String> availabilities = new HashMap<>();
}
