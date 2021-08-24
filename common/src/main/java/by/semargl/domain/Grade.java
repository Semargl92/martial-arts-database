package by.semargl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.Collections;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "grade", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Exercise> exercises = Collections.emptySet();

    @OneToMany(mappedBy = "grade", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Student> students = Collections.emptySet();
}
