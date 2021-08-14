package by.semargl.repository;

import by.semargl.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long>, JpaRepository<Student, Long> {

    @Modifying
    @Query(value = "update Student s set s.isDeleted = true, s.changed = CURRENT_TIMESTAMP where s.id = :studentID")
    void softDelete (@Param("studentID") Long id);

    @Modifying
    @Query(value = "delete from Student s where s.id = :studentID")
    void delete(@Param("studentID") Long id);

    List<Student> findByIsDeletedFalse();

    Optional<Student> findByIdAndIsDeletedFalse(Long id);
}
