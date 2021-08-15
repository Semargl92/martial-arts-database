package by.semargl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.controller.requests.GradeRequest;
import by.semargl.controller.requests.mappers.GradeMapper;
import by.semargl.domain.Grade;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.GradeRepository;
import by.semargl.repository.MartialArtRepository;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
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
}
