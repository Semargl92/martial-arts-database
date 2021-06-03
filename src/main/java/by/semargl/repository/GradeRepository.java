package by.semargl.repository;

import by.semargl.domain.Grade;

import java.util.List;

public interface GradeRepository extends CrudOperations<Long, Grade> {
    List<Grade> findGradeByQuery(Integer limit, String query);

    void batchInsert(List<Grade> grades);
}
