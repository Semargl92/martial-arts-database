package by.semargl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import by.semargl.domain.enums.Gender;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Cacheable("users")
@EqualsAndHashCode(exclude = {
        "students",  "roles"
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "login", column = @Column(name = "login")),
            @AttributeOverride(name = "password", column = @Column(name = "password"))
    })
    private Credentials credentials;

    @OneToMany(mappedBy = "user",  orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Student> students = Collections.emptySet();

    @ManyToMany(mappedBy = "users", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = Collections.emptySet();
}
