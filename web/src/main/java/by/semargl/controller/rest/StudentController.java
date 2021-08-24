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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.semargl.requests.StudentRequest;
import by.semargl.domain.Student;
import by.semargl.service.StudentService;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @ApiOperation(value = "find all students")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found")
    })
    @GetMapping("/admin")
    public List<Student> findAll() {
        return studentService.findAllStudents();
    }

    @ApiOperation(value = "find all existing students")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found")
    })
    @GetMapping
    public List<StudentRequest> findAllExisting() {
        return studentService.findAllExistingStudents();
    }

    @ApiOperation(value = "find one student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully found"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @GetMapping("/admin/{studentId}")
    public Student findOne(@PathVariable("studentId") Long id) {
        return studentService.findOneStudent(id);
    }

    @ApiOperation(value = "find one existing student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully found"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @GetMapping("/{studentId}")
    public StudentRequest findOneExisting(@PathVariable("studentId") Long id) {
        return studentService.findOneExistingStudent(id);
    }

    @ApiOperation(value = "remove student from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @DeleteMapping("/admin/{studentId}")
    public void delete(@PathVariable("studentId") Long id) {
        studentService.deleteStudent(id);
    }

    @ApiOperation(value = "set student as deleted")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for soft delete", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @PutMapping("/delete/{studentId}")
    public void softDelete(@PathVariable("studentId") Long id) {
        studentService.softDeleteStudent(id);
    }

    @ApiOperation(value = "create one student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully created")
    })
    @PostMapping
    public StudentRequest create(@RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @ApiOperation(value = "update one student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully updated"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @PutMapping("/{studentId}")
    public StudentRequest update(@PathVariable("studentId") Long id, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }

    @ApiOperation(value = "find all students for martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found"),
            @ApiResponse(code = 500, message = "There are no students for such id")
    })
    @GetMapping("/admin/for_martial_art/{martialArtId}")
    public List<Student> findAllStudentsForMartialArt(@PathVariable("martialArtId") Long id) {
        return studentService.findAllStudentsForMartialArt(id);
    }

    @ApiOperation(value = "find all students for grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "path",
                    value = "id of grade for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found"),
            @ApiResponse(code = 500, message = "There are no students for such id")
    })
    @GetMapping("/admin/for_grade/{gradeId}")
    public List<Student> findAllStudentsForGrade(@PathVariable("gradeId") Long id) {
        return studentService.findAllStudentsForGrade(id);
    }

    @ApiOperation(value = "update students grade")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", dataType = "string", paramType = "query",
                    value = "id of grade for update", required = true),
            @ApiImplicitParam(name = "studentsId", dataType = "string", paramType = "query",
                    value = "id of student for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student's grade was successfully updated"),
            @ApiResponse(code = 500, message = "There are no students or grades for such id")
    })
    @PatchMapping("/admin/update_grade")
    public Student updateStudentsGrade(@RequestParam Long studentsId, @RequestParam Long gradeId) {
        studentService.updateStudentsGrade(studentsId, gradeId);
        return studentService.findOneStudent(studentsId);
    }
}
