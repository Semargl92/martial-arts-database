package by.semargl.repository;

import by.semargl.domain.Exercise;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long>, PagingAndSortingRepository<Exercise, Long>, JpaRepository<Exercise, Long>{
}
