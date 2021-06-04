package by.semargl.repository;

import by.semargl.domain.Student;

import java.util.List;

public interface StudentRepository extends CrudOperations<Long, Student>{

    List<Student> findStudentsByQuery(Integer limit, String query);

    void batchInsert(List<Student> students);
}
