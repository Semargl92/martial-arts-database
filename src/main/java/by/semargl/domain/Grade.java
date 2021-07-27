package by.semargl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "duration_for_next_lvl")
    private Integer durationForNextLvl;

    @Column(name = "number_of_trainings_days")
    private Integer numberOfTrainingsDays;

    @Column(name = "exam_description")
    private String examDescription;

    @Column(name = "martial_art_id")
    private Long martialArtId;
}
