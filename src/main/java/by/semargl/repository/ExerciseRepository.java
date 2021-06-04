package by.semargl.repository;

import by.semargl.domain.Exercise;

import java.util.List;

public interface ExerciseRepository extends CrudOperations<Long, Exercise> {
    List<Exercise> findExerciseByQuery(Integer limit, String query);

    void batchInsert(List<Exercise> exercises);
}
