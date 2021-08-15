package by.semargl.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation("Class for creating and updating student entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    private Long userId;

    private Long gradeId;

    private Long teachersUserId;

    private LocalDateTime lastExamDate;

    private Integer numbersOfTrainingsDays;

    private LocalDateTime startDate;
}
