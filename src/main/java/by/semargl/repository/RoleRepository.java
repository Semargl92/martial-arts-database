package by.semargl.repository;

import by.semargl.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends CrudRepository<Role, Long>, PagingAndSortingRepository<Role, Long>, JpaRepository<Role, Long> {
}
