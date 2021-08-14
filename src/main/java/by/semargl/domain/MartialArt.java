package by.semargl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.MartialArtType;

@Entity
@Table(name = "martial_arts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "grades"
})
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

    @OneToMany(mappedBy = "martialArt",  orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Grade> grades = Collections.emptySet();
}
