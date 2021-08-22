package by.semargl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import by.semargl.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    @Modifying
    @Query(value = "update User u set u.isDeleted = true, u.changed = CURRENT_TIMESTAMP where u.id = :userID")
    void softDeleteUser (@Param("userID") Long id);

    /*@Query(value = "select u from User u where u.login = :userID")
    Optional<User> findByLogin(@Param("login") String login);*/

    List<User> findByIsDeletedFalse();

    Optional<User> findByIdAndIsDeletedFalse(Long id);

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String name, String surname);


}
