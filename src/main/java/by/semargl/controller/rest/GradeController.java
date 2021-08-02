package by.semargl.controller.rest;

import by.semargl.controller.requests.GradeRequest;
import by.semargl.controller.requests.mappers.GradeMapper;
import by.semargl.domain.Grade;
import by.semargl.repository.GradeRepository;
import by.semargl.repository.MartialArtRepository;
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

    private final GradeRepository gradeRepository;
    private final MartialArtRepository martialArtRepository;
    private final GradeMapper gradeMapper;

    @ApiOperation(value = "find all grades")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grades were successfully found")
    })
    @GetMapping("/all")
    public Page<Grade> findAll() {
        return gradeRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id")));
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
        return gradeRepository.findById(id).orElseThrow();
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
    public void deleteGrade(@PathVariable("gradeId") Long id) {
        gradeRepository.deleteById(id);
    }

    @ApiOperation(value = "create one grade")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully created")
    })
    @PostMapping("/create")
    public Grade createGrade(@RequestBody GradeRequest gradeRequest) {
        Grade grade = new Grade();

        gradeMapper.updateGradeFromGradeRequest(gradeRequest, grade);
        grade.setMartialArt(martialArtRepository.findById(gradeRequest.getMartialArtId()).orElseThrow());

        return gradeRepository.save(grade);
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
    public Grade updateExercise(@PathVariable("gradeId") Long id, @RequestBody GradeRequest gradeRequest) {
        Grade grade = gradeRepository.findById(id).orElseThrow();

        gradeMapper.updateGradeFromGradeRequest(gradeRequest, grade);

        return gradeRepository.save(grade);
    }
}
