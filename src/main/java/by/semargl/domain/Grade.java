package by.semargl.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "exercises", "students"
})
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

    @ManyToOne
    @JoinColumn(name = "martial_art_id")
    @JsonBackReference
    private MartialArt martialArt;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Exercise> exercises = Collections.emptySet();

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Student> students = Collections.emptySet();
}
