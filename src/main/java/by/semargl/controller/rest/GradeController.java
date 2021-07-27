package by.semargl.controller.rest;

import by.semargl.domain.Grade;
import by.semargl.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeRepository gradeRepository;

    @GetMapping
    public Page<Grade> findAll() {
        System.out.println("grade controller");
        return gradeRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id")));
    }
}
