package by.semargl.controller.rest;

import by.semargl.controller.requests.StudentRequest;
import by.semargl.controller.requests.mappers.StudentMapper;
import by.semargl.domain.Student;
import by.semargl.repository.StudentRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @ApiOperation(value = "find all students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found")
    })
    @GetMapping("/all/admin")
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @ApiOperation(value = "find all existing students")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students were successfully found")
    })
    @GetMapping("/all")
    public List<Student> findAllExisting() {
        return studentRepository.findByIsDeletedFalse();
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
    @GetMapping("/{studentId}")
    public Student findOne(@PathVariable("studentId") Long id) {
        return studentRepository.findById(id).orElseThrow();
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
    public void deleteStudent(@PathVariable("studentId") Long id) {
        studentRepository.deleteById(id);
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
    public void softDeleteStudent(@PathVariable("studentId") Long id) {
        studentRepository.softDelete(id);
    }

    @ApiOperation(value = "create one student")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student was successfully created")
    })
    @PostMapping("/create")
    public Student createUser(@RequestBody StudentRequest studentRequest) {
        Student student = new Student();

        studentMapper.updateStudentFromStudentRequest(studentRequest, student);
        student.setCreated(LocalDateTime.now());
        student.setChanged(LocalDateTime.now());
        student.setIsDeleted(false);

        return studentRepository.save(student);
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
    public Student updateUser(@PathVariable("studentId") Long id, @RequestBody StudentRequest studentRequest) {
        Student student = studentRepository.findById(id).orElseThrow();

        studentMapper.updateStudentFromStudentRequest(studentRequest, student);
        student.setChanged(LocalDateTime.now());

        return studentRepository.save(student);
    }
}
