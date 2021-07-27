package by.semargl.domain;

import by.semargl.domain.enums.ExerciseType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "grade_id")
    private Long gradeId;

    @Column
    private String description;

    @Column
    private String resources;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType = ExerciseType.NOT_SELECTED;

    @Column(name = "is_weapon_technik")
    private Boolean isWeaponTechnik;
}
