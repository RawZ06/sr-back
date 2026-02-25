package fr.rawz06.srback.core.model;

import fr.rawz06.srback.core.exception.domain.IllegalMatchStateException;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Match {
    private final Long id;
    private final String player1;
    private final String player2;
    private final LocalDateTime datetime;
    private final String round;
    private Restreamer host;
    private Restreamer cohost;
    private boolean visible;
    private final Map<Restreamer, RestreamerAvailability> availabilities;

    public Match(
            Long id,
            @NonNull String player1,
            @NonNull String player2,
            @NonNull LocalDateTime datetime,
            @NonNull String round,
            Restreamer host,
            Restreamer cohost,
            boolean visible,
            Map<Restreamer, RestreamerAvailability> availabilities
    ) {
        if(player1.equals(player2)) {
            throw new IllegalMatchStateException("player1 and player2 are the same");
        }
        if(host != null && host.equals(cohost)) {
            throw new IllegalMatchStateException("host and cohost are the same");
        }
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.datetime = datetime;
        this.round = round;
        this.host = host;
        this.cohost = cohost;
        this.visible = visible;
        this.availabilities = availabilities == null ? new HashMap<>() : new HashMap<>(availabilities);
    }

    public Match(
            Long id,
            @NonNull String player1,
            @NonNull String player2,
            @NonNull LocalDateTime datetime,
            @NonNull String round
    ) {
        this(id, player1, player2, datetime, round, null, null, false, new HashMap<>());
    }

    public Match(
            @NonNull String player1,
            @NonNull String player2,
            @NonNull LocalDateTime datetime,
            @NonNull String round
    ) {
        this(null, player1, player2, datetime, round);
    }

    public void assignStaff(Restreamer host, Restreamer cohost) {
        if(host != null && host.equals(cohost)) {
            throw new IllegalMatchStateException("host and cohost are the same");
        }
        this.host = host;
        this.cohost = cohost;
    }

    public void toggleVisibility() {
        this.visible = !this.visible;
    }

    public void changeAvailability(Restreamer restreamer, RestreamerAvailability availability) {
        if(restreamer == null || availability == null) {
            throw new IllegalMatchStateException("restreamer or availability are null");
        }
        this.availabilities.put(restreamer, availability);
    }

    public Map<Restreamer, RestreamerAvailability> getAvailabilities() {
        return Map.copyOf(availabilities);
    }
}
