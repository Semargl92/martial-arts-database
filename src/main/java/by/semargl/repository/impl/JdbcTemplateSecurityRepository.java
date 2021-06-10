package by.semargl.repository.impl;

import by.semargl.domain.Security;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.SecurityColumn;
import by.semargl.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcTemplateSecurityRepository implements SecurityRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateSecurityRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Security> findAll() {
        return jdbcTemplate.query("select * from security order by id desc", this::getSecurityRowMapper);
    }

    @Override
    public Security findOne(Long id) throws NoSuchEntityException {
        Security chosenOne;
        try {
            final String findOneWithNameParam = "select * from security where id = :number";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("number", id);

            chosenOne = namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getSecurityRowMapper);
        } catch (EmptyResultDataAccessException empty) {
            throw new NoSuchEntityException("There is no such security entity");
        }
        return chosenOne;
    }

    @Override
    public Security save(Security entity) {
        final String createQuery = "insert into security (role, user_id, is_deleted, created, changed) " +
                "values (:role, :userId, :isDeleted, :created, :changed);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        entity.setCreated(new Date(System.currentTimeMillis()));
        entity.setChanged(new Date(System.currentTimeMillis()));
        MapSqlParameterSource params = generateSecurityParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public Security update(Security entity) throws NoSuchEntityException {
        if (entity.getId() == null) {
            throw new NoSuchEntityException("There is no id data for this security entity");
        }

        try {
            findOne(entity.getId());
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such security entity to update");
        }

        final String updateQuery = "update security set role=:role, user_id=:userId, is_deleted=:isDeleted, " +
                "changed=:changed where id=:id;";

        entity.setChanged(new Date(System.currentTimeMillis()));
        MapSqlParameterSource params = generateSecurityParamsMap(entity);
        params.addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(updateQuery, params);
        return findOne(entity.getId());
    }

    @Override
    public boolean delete(Long id) throws NoSuchEntityException {
        try {
            findOne(id);
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such security entity to delete");
        }

        final String deleteQuery = "delete from security where id =:id;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteQuery, params);
        return true;
    }

    @Override
    public List<Security> findSecurityByQuery(Integer limit, String query) {
        final String searchQuery = "select * from security where role like :query limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getSecurityRowMapper);
    }

    @Override
    public void batchInsert(List<Security> entities) {
        final String createQuery = "insert into security (role, user_id, is_deleted, created, changed) " +
                "values (:role, :userId, :isDeleted, :created, :changed);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Security entity : entities) {
            entity.setCreated(new Date(System.currentTimeMillis()));
            entity.setChanged(new Date(System.currentTimeMillis()));
            batchParams.add(generateSecurityParamsMap(entity));
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private MapSqlParameterSource generateSecurityParamsMap(Security entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("role", entity.getRole());
        params.addValue("userId", entity.getUserId());
        params.addValue("isDeleted", entity.getIsDeleted());
        params.addValue("created", entity.getCreated());
        params.addValue("changed", entity.getChanged());

        return params;
    }

    private Security getSecurityRowMapper(ResultSet rs, int i) throws SQLException {
        Security security = new Security();
        security.setId(rs.getLong(SecurityColumn.ID));
        security.setRole(rs.getString(SecurityColumn.ROLE));
        security.setUserId(rs.getLong(SecurityColumn.USER_ID));
        security.setIsDeleted(rs.getBoolean(SecurityColumn.IS_DELETED));
        security.setCreated(rs.getDate(SecurityColumn.CREATED));
        security.setChanged(rs.getDate(SecurityColumn.CHANGED));

        return security;
    }
}
