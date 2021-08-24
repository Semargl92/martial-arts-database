package by.semargl.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.Gender;

@ApiOperation("Class for updating user entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String name;

    private String surname;

    private Gender gender;

    private float weight;

    private LocalDateTime birthDate;

    private String login;
}
