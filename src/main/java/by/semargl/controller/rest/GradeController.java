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
import org.springframework.data.domain.Page;

import by.semargl.controller.requests.GradeRequest;
import by.semargl.domain.Grade;
import by.semargl.service.GradeService;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @ApiOperation(value = "find all grades")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grades were successfully found")
    })
    @GetMapping
    public Page<Grade> findAll() {
        return gradeService.findAllGrades();
    }

    @ApiOperation(value = "find one grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
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
                    value = "id of grade for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no grade with such id")
    })
    @DeleteMapping("/admin/{gradeId}")
    public void delete(@PathVariable("gradeId") Long id) {
        gradeService.deleteGrade(id);
    }

    @ApiOperation(value = "remove grade with orphans")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no grade with such id")
    })
    @DeleteMapping("/admin/delete_with_orphans/{gradeId}")
    public void deleteWitOrphans(@PathVariable("gradeId") Long id) {
        gradeService.deleteGradeWithOrphans(id);
    }

    @ApiOperation(value = "create one grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully created")
    })
    @PostMapping("/admin")
    public Grade create(@RequestBody GradeRequest gradeRequest) {
        return gradeService.createGrade(gradeRequest);
    }

    @ApiOperation(value = "update one grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grade was successfully updated"),
            @ApiResponse(code = 500, message = "There is no grade with such id")
    })
    @PutMapping("/admin/{gradeId}")
    public Grade update(@PathVariable("gradeId") Long id, @RequestBody GradeRequest gradeRequest) {
        return gradeService.updateGrade(id, gradeRequest);
    }

    @ApiOperation(value = "find all grades by martial art id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grades were successfully found")
    })
    @GetMapping("all_for_id/{martialArtId}")
    public List<Grade> findAllGradesForMartialArtId(@PathVariable("martialArtId") Long id) {
        return gradeService.findAllGradesByMartialArtId(id);
    }

    @ApiOperation(value = "find all grades by martial art name")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtName", dataType = "string", paramType = "path",
                    value = "name of martial art for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Grades were successfully found")
    })
    @GetMapping("all_for_name/{martialArtName}")
    public List<Grade> findAllGradesForMartialArtName(@PathVariable("martialArtName") String martialArtName) {
        return gradeService.findAllGradesByMartialArtName(martialArtName);
    }
}
