package by.semargl.repository;

import by.semargl.domain.User;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.isDeleted = true where u.id = :userID")
    void softDelete (@Param("userID") Long id);

    @Cacheable("users")
    List<User> findByIsDeletedFalse();

    @Cacheable("users")
    @Query(value = "select u from User u where u.id < :userID")
    List<User> findByIdHQLVersion(@Param("userID") Long id);

}
