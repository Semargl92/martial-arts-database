package by.semargl.domain;

import by.semargl.domain.enums.MartialArtType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "martial_arts")
@Data
@NoArgsConstructor
public class MartialArt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MartialArtType martialArtType = MartialArtType.NOT_SELECTED;

    @Column
    private String origin;

    @Column(name = "foundation_date")
    private LocalDateTime foundationDate;

    @Column
    private String description;

    @Column(name = "weapon_technics_available")
    private Boolean weaponTechnicsAvailable;
}
