package by.semargl.repository.springdata;

import by.semargl.domain.hibernate.HibernateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDataRepository extends CrudRepository<HibernateUser, Long>, PagingAndSortingRepository<HibernateUser, Long>, JpaRepository<HibernateUser, Long> {

    List<HibernateUser> findByIdIn(List<Long> ids);

    Optional<HibernateUser> findByNameAndLogin(String name, String login);

    @Query(value = "select u from HibernateUser u where u.id < :userID")
    List<HibernateUser> findByIdHQLVersion(@Param("userID") Long id);

}
