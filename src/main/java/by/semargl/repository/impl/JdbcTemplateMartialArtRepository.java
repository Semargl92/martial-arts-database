package by.semargl.repository.impl;

import by.semargl.domain.MartialArt;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.MartialArtColumn;
import by.semargl.repository.MartialArtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Component
@Primary

public class JdbcTemplateMartialArtRepository implements MartialArtRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateMartialArtRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<MartialArt> findAll() {
        return jdbcTemplate.query("select * from martial_arts order by id", this::getMartialArtRowMapper);
    }

    @Override
    public MartialArt findOne(Long id) throws NoSuchEntityException {
        MartialArt chosenOne;
        try {
            final String findOneWithNameParam = "select * from martial_arts where id = :number";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("number", id);

            chosenOne = namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getMartialArtRowMapper);
        } catch (EmptyResultDataAccessException empty) {
            throw new NoSuchEntityException("There is no such martial art");
        }
        return chosenOne;
    }

    @Override
    public MartialArt save(MartialArt entity) {
        final String createQuery = "insert into martial_arts (name, type, origin, foundation_date, description, weapon_technics_available) " +
                "values (:name, :type, :origin, :foundationDate, :description, :weaponTechnicsAvailable);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateMartialArtParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdMartialArtId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdMartialArtId);
    }

    @Override
    public MartialArt update(MartialArt entity) throws NoSuchEntityException {
        if (entity.getId() == null) {
            throw new NoSuchEntityException("There is no id data for this martial art");
        }

        try {
            findOne(entity.getId());
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such martial art to update");
        }

        final String updateQuery = "update martial_arts set name=:name, type=:type, origin=:origin, foundation_date=:foundationDate, " +
                "description=:description, weapon_technics_available=:weaponTechnicsAvailable where id=:id;";

        MapSqlParameterSource params = generateMartialArtParamsMap(entity);
        params.addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(updateQuery, params);
        return findOne(entity.getId());
    }

    @Override
    public boolean delete(Long id) throws NoSuchEntityException {
        try {
            findOne(id);
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such martial art to delete");
        }

        final String deleteQuery = "delete from martial_arts where id =:id;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteQuery, params);
        return true;
    }

    @Override
    public List<MartialArt> findMartialArtByQuery(Integer limit, String query) {
        final String searchQuery = "select * from martial_arts where name like :query limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getMartialArtRowMapper);
    }

    @Override
    public void batchInsert(List<MartialArt> martialArts) {
        final String createQuery = "insert into martial_arts (name, type, origin, foundation_date, description, weapon_technics_available) " +
                "values (:name, :type, :origin, :foundationDate, :description, :weaponTechnicsAvailable);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (MartialArt art : martialArts) {
            batchParams.add(generateMartialArtParamsMap(art));
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private MapSqlParameterSource generateMartialArtParamsMap(MartialArt entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("type", entity.getType());
        params.addValue("origin", entity.getOrigin());
        params.addValue("foundationDate", entity.getFoundationDate());
        params.addValue("description", entity.getDescription());
        params.addValue("weaponTechnicsAvailable", entity.getWeaponTechnicsAvailable());

        return params;
    }

    private MartialArt getMartialArtRowMapper(ResultSet rs, int i) throws SQLException {
        MartialArt martialArt = new MartialArt();
        martialArt.setId(rs.getLong(MartialArtColumn.ID));
        martialArt.setName(rs.getString(MartialArtColumn.NAME));
        martialArt.setType(rs.getString(MartialArtColumn.TYPE));
        martialArt.setOrigin(rs.getString(MartialArtColumn.ORIGIN));
        martialArt.setFoundationDate(rs.getDate(MartialArtColumn.FOUNDATION_DATE));
        martialArt.setDescription(rs.getString(MartialArtColumn.DESCRIPTION));
        martialArt.setWeaponTechnicsAvailable(rs.getBoolean(MartialArtColumn.WEAPON_TECHNICS_AVAILABLE));

        return martialArt;
    }
}
