package by.semargl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import by.semargl.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>, PagingAndSortingRepository<Role, Long>, JpaRepository<Role, Long> {
}
