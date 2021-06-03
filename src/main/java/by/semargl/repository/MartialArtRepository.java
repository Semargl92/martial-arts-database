package by.semargl.repository;

import by.semargl.domain.MartialArt;

import java.util.List;

public interface MartialArtRepository extends CrudOperations<Long, MartialArt> {

    List<MartialArt> findMartialArtByQuery(Integer limit, String query);

    void batchInsert(List<MartialArt> martialArts);
}
