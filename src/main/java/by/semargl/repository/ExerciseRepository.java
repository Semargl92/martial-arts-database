package by.semargl.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import by.semargl.domain.Exercise;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long>, PagingAndSortingRepository<Exercise, Long>, JpaRepository<Exercise, Long>{

    @Modifying
    @Query(value = "delete from Exercise e where e.id = :exerciseID")
    void delete(@Param("exerciseID") Long id);
}
