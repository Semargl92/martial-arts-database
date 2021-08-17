package by.semargl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import by.semargl.domain.Exercise;
import by.semargl.domain.Grade;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long>, PagingAndSortingRepository<Exercise, Long>, JpaRepository<Exercise, Long>{

    @Modifying
    @Query(value = "delete from Exercise e where e.id = :exerciseID")
    void delete(@Param("exerciseID") Long id);

    @Modifying
    @Query(value = "delete from Exercise e where e.grade = :grade")
    void deleteWithGrade(@Param("grade") Grade grade);

    List<Exercise> findByGradeId(Long id);

    List<Exercise> findByGradeIsIn(List<Grade> grade);

    List<Exercise> findByNameContainingIgnoreCase(String exerciseName);
}
