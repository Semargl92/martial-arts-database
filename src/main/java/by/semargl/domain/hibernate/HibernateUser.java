package by.semargl.domain.hibernate;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class HibernateUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String login;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.NOT_SELECTED;

    @Column
    private Float weight;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime changed;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;
}
