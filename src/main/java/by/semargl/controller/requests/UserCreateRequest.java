package by.semargl.controller.requests;

import by.semargl.domain.Credentials;
import by.semargl.domain.enums.Gender;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiOperation("Class for creating user entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String name;

    private String surname;

    private Gender gender;

    private float weight;

    private LocalDateTime birthDate;

    private Credentials credentials;
}