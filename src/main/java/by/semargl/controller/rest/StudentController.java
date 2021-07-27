package by.semargl.controller.rest;

import by.semargl.domain.Student;
import by.semargl.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    public List<Student> findAll() {
        System.out.println("grade controller");
        return studentRepository.findAll();
    }
}
