package by.semargl.controller.rest;

import by.semargl.controller.requests.StudentRequest;
import by.semargl.domain.Student;
import by.semargl.service.StudentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @ApiOperation(value = "find all students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found")
    })
    @GetMapping("/all/admin")
    public List<Student> findAll() {
        return studentService.findAllStudents();
    }

    @ApiOperation(value = "find all existing students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found")
    })
    @GetMapping("/all")
    public List<StudentRequest> findAllExisting() {
        return studentService.findAllExistingStudents();
    }

    @ApiOperation(value = "find one student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for search", required = true)
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
                    value = "id of student for search", required = true)
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
                    value = "id of student for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @DeleteMapping("/delete/admin/{studentId}")
    public void delete(@PathVariable("studentId") Long id) {
        studentService.deleteStudent(id);
    }

    @ApiOperation(value = "set student as deleted")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for soft delete", required = true)
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully created")
    })
    @PostMapping("/create")
    public Student create(@RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @ApiOperation(value = "update one student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", dataType = "string", paramType = "path",
                    value = "id of student for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully updated"),
            @ApiResponse(code = 500, message = "There is no student with such id")
    })
    @PutMapping("/update/{studentId}")
    public Student update(@PathVariable("studentId") Long id, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }
}
