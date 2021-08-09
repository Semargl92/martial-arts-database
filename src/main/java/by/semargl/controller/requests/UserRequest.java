package by.semargl.controller.requests;

import by.semargl.domain.enums.Gender;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
