package by.semargl.repository;

import by.semargl.domain.Student;

import java.util.List;

public interface StudentRepository extends CrudOperations<Long, Student>{

    List<Student> findStudentsByGrade(Integer limit, Long grade);

    void batchInsert(List<Student> students);
}
