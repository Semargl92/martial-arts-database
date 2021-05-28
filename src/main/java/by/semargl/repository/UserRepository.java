package by.semargl.repository;

import by.semargl.domain.User;

import java.util.List;

public interface UserRepository extends CrudOperations<Long, User> {

    List<User> findUsersByQuery(Integer limit, String query);

    void batchInsert(List<User> users);
}
