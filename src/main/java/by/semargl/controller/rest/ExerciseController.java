package by.semargl.controller.rest;

import by.semargl.domain.Exercise;
import by.semargl.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    @GetMapping
    public List<Exercise> findAll() {
        System.out.println("exercise controller");
        return exerciseRepository.findAll();
    }
}
