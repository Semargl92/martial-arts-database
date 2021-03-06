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
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
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
                    value = "id of exercise for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
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
                    value = "id of exercise for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no exercise with such id")
    })
    @DeleteMapping("/admin/{exerciseId}")
    public void delete(@PathVariable("exerciseId") Long id) {
        exerciseService.deleteExercise(id);
    }

    @ApiOperation(value = "create one exercise")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully created")
    })
    @PostMapping("/admin")
    public Exercise create(@RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.createExercise(exerciseRequest);
    }

    @ApiOperation(value = "update one exercise")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exerciseId", dataType = "string", paramType = "path",
                    value = "id of exercise for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercise was successfully updated"),
            @ApiResponse(code = 500, message = "There is no exercise with such id")
    })
    @PutMapping("/admin/{exerciseId}")
    public Exercise update(@PathVariable("exerciseId") Long id, @RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.updateExercise(id, exerciseRequest);
    }

    @ApiOperation(value = "find all exercises for grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercises were successfully found"),
            @ApiResponse(code = 500, message = "There is no exercises for such grade id")
    })
    @GetMapping("all_for_grade/{gradeId}")
    public List<Exercise> findAllForGrade(@PathVariable("gradeId") Long id) {
        return exerciseService.findAllWithGradeId(id);
    }

    @ApiOperation(value = "find all exercises for martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercises were successfully found"),
            @ApiResponse(code = 500, message = "There is no exercises for such martial art id")
    })
    @GetMapping("all_for_martial_art/{martialArtId}")
    public List<Exercise> findAllForMartialArt(@PathVariable("martialArtId") Long id) {
        return exerciseService.findAllWithMartialArtId(id);
    }

    @ApiOperation(value = "find exercises by name")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exerciseName", dataType = "string", paramType = "query",
                    value = "name of exercise for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exercises were successfully found"),
            @ApiResponse(code = 500, message = "There is no exercises with such name")
    })
    @GetMapping("/name")
    public List<Exercise> findExercisesByName(@RequestParam String exerciseName) {
        return exerciseService.findExerciseByName(exerciseName);
    }
}
