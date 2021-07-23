package by.semargl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Cacheable("users")
public class User {

    private Long id;

    private String name;

    private String surname;

    private String login;

    private String gender;

    private Float weight;

    private Boolean isDeleted;

    private Date created;

    private Date changed;

    private Date birthDate;

    public User(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
