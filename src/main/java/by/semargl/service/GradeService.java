package by.semargl.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.controller.requests.GradeRequest;
import by.semargl.controller.requests.mappers.GradeMapper;
import by.semargl.domain.Grade;
import by.semargl.domain.MartialArt;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.ExerciseRepository;
import by.semargl.repository.GradeRepository;
import by.semargl.repository.MartialArtRepository;
import by.semargl.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final ExerciseRepository exerciseRepository;
    private final StudentRepository studentRepository;
    private final MartialArtRepository martialArtRepository;
    private final GradeMapper gradeMapper;

    public Page<Grade> findAllGrades() {
        return gradeRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id")));
    }

    public Grade findOneGrade(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Grade not found by id " + id));
    }

    @Transactional
    public void deleteGrade(Long id) {
        gradeRepository.delete(id);
    }

    @Transactional
    public void deleteGradeWithOrphans(Long id) {
        Grade grade = findOneGrade(id);
        studentRepository.deleteWithGrade(grade);
        exerciseRepository.deleteWithGrade(grade);
        deleteGrade(id);
    }

    public Grade createGrade(GradeRequest gradeRequest) {
        Grade grade = new Grade();

        gradeMapper.updateGradeFromGradeRequest(gradeRequest, grade);
        grade.setMartialArt(martialArtRepository.findById(gradeRequest.getMartialArtId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such martial art for this grade")));

        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Long id, GradeRequest gradeRequest) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Grade not found by id " + id));

        gradeMapper.updateGradeFromGradeRequest(gradeRequest, grade);
        if (gradeRequest.getMartialArtId() != null ) {
            grade.setMartialArt(martialArtRepository.findById(gradeRequest.getMartialArtId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such martial art for this grade")));
        }

        return gradeRepository.save(grade);
    }

    public List<Grade> findAllGradesByMartialArtId(Long id) {
        List<Grade> grades = gradeRepository.findByMartialArtId(id);
        if (grades.isEmpty()) {
            throw new NoSuchEntityException("There is no grades for this martial art id");
        }
        return grades;
    }

    public List<Grade> findAllGradesByMartialArtName(String martialArtName) {
        MartialArt martialArt = martialArtRepository.findByName(martialArtName)
                .orElseThrow(() -> new NoSuchEntityException("There is no martial art with such name"));
        return findAllGradesByMartialArtId(martialArt.getId());
    }
}
