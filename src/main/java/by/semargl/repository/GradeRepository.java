package by.semargl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import by.semargl.domain.Grade;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long>, PagingAndSortingRepository<Grade, Long>, JpaRepository<Grade, Long> {

    @Modifying
    @Query(value = "delete from Grade g where g.id = :gradeID")
    void delete(@Param("gradeID") Long id);

    List<Grade> findByMartialArtId(Long id);
}
