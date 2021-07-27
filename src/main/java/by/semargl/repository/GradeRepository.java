package by.semargl.repository;

import by.semargl.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long>, PagingAndSortingRepository<Grade, Long>, JpaRepository<Grade, Long> {
}
