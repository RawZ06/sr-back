package fr.rawz06.srback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restreamers")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RestreamerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
