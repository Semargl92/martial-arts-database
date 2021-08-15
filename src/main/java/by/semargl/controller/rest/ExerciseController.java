package by.semargl.controller.rest;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.semargl.controller.requests.ExerciseRequest;
import by.semargl.domain.Exercise;
import by.semargl.service.ExerciseService;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @ApiOperation(value = "find all exercises")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercises were successfully found")
    })
    @GetMapping
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
    @DeleteMapping("/{exerciseId}")
    public void delete(@PathVariable("exerciseId") Long id) {
        exerciseService.deleteExercise(id);
    }

    @ApiOperation(value = "create one exercise")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully created")
    })
    @PostMapping
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
    @PutMapping("/{exerciseId}")
    public Exercise update(@PathVariable("exerciseId") Long id, @RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.updateExercise(id, exerciseRequest);
    }
}
