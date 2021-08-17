package by.semargl.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.controller.requests.ExerciseRequest;
import by.semargl.controller.requests.mappers.ExerciseMapper;
import by.semargl.domain.Exercise;
import by.semargl.domain.Grade;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.ExerciseRepository;
import by.semargl.repository.GradeRepository;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final GradeRepository gradeRepository;
    private final ExerciseMapper exerciseMapper;

    public List<Exercise> findAllExercise() {
        return exerciseRepository.findAll();
    }

    public Exercise findOneExercise(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Exercise not found by id " + id));
    }

    @Transactional
    public void deleteExercise(Long id) {
        exerciseRepository.delete(id);
    }

    public Exercise createExercise(ExerciseRequest exerciseRequest) {
        Exercise exercise = new Exercise();

        exerciseMapper.updateExerciseFromExerciseRequest(exerciseRequest, exercise);
        exercise.setGrade(gradeRepository.findById(exerciseRequest.getGradeId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such grade for this exercise")));

        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(Long id, ExerciseRequest exerciseRequest) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Exercise not found by id " + id));

        exerciseMapper.updateExerciseFromExerciseRequest(exerciseRequest, exercise);
        if (exerciseRequest.getGradeId() != null ) {
            exercise.setGrade(gradeRepository.findById(exerciseRequest.getGradeId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such grade for this exercise")));
        }

        return exerciseRepository.save(exercise);
    }

    public List<Exercise> findAllWithGradeId(Long id) {
        List <Exercise> exercises = exerciseRepository.findByGradeId(id);
        if (exercises.isEmpty()) {
            throw new NoSuchEntityException("There is no exercises for this grade id");
        }
        return exercises;
    }

    public List<Exercise> findAllWithMartialArtId(Long id) {
        List<Grade> allGrades = gradeRepository.findByMartialArtId(id);
        if (allGrades.isEmpty()) {
            throw new NoSuchEntityException("There is no grades for this martial art id");
        }

        List <Exercise> exercises = exerciseRepository.findByGradeIsIn(allGrades);

        if (exercises.isEmpty()) {
            throw new NoSuchEntityException("There is no exercises for this martial art id");
        }
        return exercises;
    }
}
