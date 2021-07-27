package by.semargl.repository;

import by.semargl.domain.MartialArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MartialArtRepository extends CrudRepository<MartialArt, Long>, PagingAndSortingRepository<MartialArt, Long>, JpaRepository<MartialArt, Long> {
}
