package by.semargl.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation("Class for creating and updating grade entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequest {

    private String name;

    private Integer durationForNextLvl;

    private Integer numberOfTrainingsDays;

    private String examDescription;

    private Long martialArtId;
}
