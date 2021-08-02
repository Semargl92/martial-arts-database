package by.semargl.repository;

import by.semargl.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long>, JpaRepository<Student, Long> {

    @Modifying
    @Transactional
    @Query(value = "update Student s set s.isDeleted = true where s.id = :studentID")
    void softDelete (@Param("studentID") Long id);

    List<Student> findByIsDeletedFalse();
}
