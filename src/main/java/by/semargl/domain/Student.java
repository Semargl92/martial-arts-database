package by.semargl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "teachers_user_id")
    private Long teachersUserId;

    @Column(name = "last_exam_date")
    private LocalDateTime lastExamDate;

    @Column(name = "numbers_of_trainings_days")
    private Integer numbersOfTrainingsDays;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime changed;
}
