package fr.rawz06.srback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restream_availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestreamAvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RestreamerAvailabilityEnum availability;

    @ManyToOne
    @JoinColumn(name = "restreamer_id", nullable = false)
    private RestreamerEntity restreamer;
}
