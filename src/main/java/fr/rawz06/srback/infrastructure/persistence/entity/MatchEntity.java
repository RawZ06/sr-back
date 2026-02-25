package fr.rawz06.srback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String player1;

    @Column(nullable = false)
    private String player2;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private String round;

    @Column(nullable = false)
    private boolean visible;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "host_id")
    private RestreamerEntity host;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cohost_id")
    private RestreamerEntity cohost;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id",  nullable = false)
    private List<RestreamAvailabilityEntity> restreamAvailabilityEntities = new ArrayList<>();
}
