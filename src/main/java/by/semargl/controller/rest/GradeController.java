package by.semargl.controller.rest;

import by.semargl.controller.requests.GradeRequest;
import by.semargl.controller.requests.mappers.GradeMapper;
import by.semargl.domain.Grade;
import by.semargl.repository.GradeRepository;
import by.semargl.repository.MartialArtRepository;
import by.semargl.service.GradeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @ApiOperation(value = "find all grades")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grades were successfully found")
    })
    @GetMapping("/all")
    public Page<Grade> findAll() {
        return gradeService.findAllGrades();
    }

    @ApiOperation(value = "find one grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully found"),
            @ApiResponse(code = 500, message = "There is no grade with such id")
    })
    @GetMapping("/{gradeId}")
    public Grade findOne(@PathVariable("gradeId") Long id) {
        return gradeService.findOneGrade(id);
    }

    @ApiOperation(value = "remove grade from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no grade with such id")
    })
    @DeleteMapping("/delete/{gradeId}")
    public void delete(@PathVariable("gradeId") Long id) {
        gradeService.deleteGrade(id);
    }

    @ApiOperation(value = "create one grade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully created")
    })
    @PostMapping("/create")
    public Grade create(@RequestBody GradeRequest gradeRequest) {
        return gradeService.createGrade(gradeRequest);
    }

    @ApiOperation(value = "update one grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully updated"),
            @ApiResponse(code = 500, message = "There is no grade with such id")
    })
    @PutMapping("/update/{gradeId}")
    public Grade update(@PathVariable("gradeId") Long id, @RequestBody GradeRequest gradeRequest) {
        return gradeService.updateGrade(id, gradeRequest);
    }
}
