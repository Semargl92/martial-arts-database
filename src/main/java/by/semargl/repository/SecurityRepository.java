package by.semargl.repository;

import by.semargl.domain.Security;

import java.util.List;

public interface SecurityRepository extends CrudOperations<Long, Security> {
    List<Security> findSecurityByQuery(Integer limit, String query);

    void batchInsert(List<Security> securities);
}
