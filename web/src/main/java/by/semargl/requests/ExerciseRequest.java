package by.semargl.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.ExerciseType;

@ApiOperation("Class for creating and updating exercise entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequest {

    private String name;

    private Long gradeId;

    private String description;

    private String resources;

    private ExerciseType exerciseType;

    private Boolean isWeaponTechnik;
}
