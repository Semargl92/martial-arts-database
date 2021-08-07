package by.semargl.controller.rest;

import by.semargl.controller.requests.ExerciseRequest;
import by.semargl.controller.requests.mappers.ExerciseMapper;
import by.semargl.domain.Exercise;
import by.semargl.repository.ExerciseRepository;
import by.semargl.repository.GradeRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final GradeRepository gradeRepository;
    private final ExerciseMapper exerciseMapper;

    @ApiOperation(value = "find all exercises")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercises were successfully found")
    })
    @GetMapping("/all")
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @ApiOperation(value = "find one exercise")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exerciseId", dataType = "string", paramType = "path",
                    value = "id of exercise for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully found"),
            @ApiResponse(code = 500, message = "There is no exercise with such id")
    })
    @GetMapping("/{exerciseId}")
    public Exercise findOne(@PathVariable("exerciseId") Long id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    @ApiOperation(value = "remove exercise from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exerciseId", dataType = "string", paramType = "path",
                    value = "id of exercise for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no exercise with such id")
    })
    @DeleteMapping("/delete/{exerciseId}")
    public void deleteExercise(@PathVariable("exerciseId") Long id) {
        exerciseRepository.deleteById(id);
    }

    @ApiOperation(value = "create one exercise")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully created")
    })
    @PostMapping("/create")
    public Exercise createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        Exercise exercise = new Exercise();

        exerciseMapper.updateExerciseFromExerciseRequest(exerciseRequest, exercise);
        exercise.setGrade(gradeRepository.findById(exerciseRequest.getGradeId()).orElseThrow());

        return exerciseRepository.save(exercise);
    }

    @ApiOperation(value = "update one exercise")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exerciseId", dataType = "string", paramType = "path",
                    value = "id of exercise for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully updated"),
            @ApiResponse(code = 500, message = "There is no exercise with such id")
    })
    @PutMapping("/update/{exerciseId}")
    public Exercise updateExercise(@PathVariable("exerciseId") Long id, @RequestBody ExerciseRequest exerciseRequest) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();

        exerciseMapper.updateExerciseFromExerciseRequest(exerciseRequest, exercise);
        if (exerciseRequest.getGradeId() != null ) {
            exercise.setGrade(gradeRepository.findById(exerciseRequest.getGradeId()).orElseThrow());
        }

        return exerciseRepository.save(exercise);
    }
}
