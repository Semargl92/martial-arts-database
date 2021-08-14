package by.semargl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.ExerciseType;

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

    @Column
    private String description;

    @Column
    private String resources;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType = ExerciseType.NOT_SELECTED;

    @Column(name = "is_weapon_technik")
    private Boolean isWeaponTechnik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    @JsonBackReference
    private Grade grade;
}
