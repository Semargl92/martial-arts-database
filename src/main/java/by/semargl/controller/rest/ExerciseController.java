package by.semargl.controller.rest;

import by.semargl.controller.requests.ExerciseRequest;
import by.semargl.domain.Exercise;
import by.semargl.service.ExerciseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @ApiOperation(value = "find all exercises")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercises were successfully found")
    })
    @GetMapping("/all")
    public List<Exercise> findAll() {
        return exerciseService.findAllExercise();
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
        return exerciseService.findOneExercise(id);
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
    public void delete(@PathVariable("exerciseId") Long id) {
        exerciseService.deleteExercise(id);
    }

    @ApiOperation(value = "create one exercise")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully created")
    })
    @PostMapping("/create")
    public Exercise create(@RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.createExercise(exerciseRequest);
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
    public Exercise update(@PathVariable("exerciseId") Long id, @RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.updateExercise(id, exerciseRequest);
    }
}
