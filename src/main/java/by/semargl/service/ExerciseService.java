package by.semargl.service;

import by.semargl.controller.requests.ExerciseRequest;
import by.semargl.controller.requests.mappers.ExerciseMapper;
import by.semargl.domain.Exercise;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.ExerciseRepository;
import by.semargl.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
