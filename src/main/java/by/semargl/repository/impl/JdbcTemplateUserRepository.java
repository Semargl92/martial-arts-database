package by.semargl.repository.impl;

import by.semargl.domain.User;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.UserColumn;
import by.semargl.repository.UserRepository;
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

public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users order by id desc", this::getUserRowMapper);
    }

    @Override
    public User findOne(Long id) throws NoSuchEntityException {
        User chosenOne;
        try {
            final String findOneWithNameParam = "select * from users where id = :number";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("number", id);

            chosenOne = namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getUserRowMapper);
        } catch (EmptyResultDataAccessException empty) {
            throw new NoSuchEntityException("There is no such user");
        }
        return chosenOne;
    }

    @Override
    public User save(User entity) {
        final String createQuery = "insert into users (name, surname, login, gender, weight, is_deleted, created, changed, birth_date) " +
                "values (:name, :surname, :login, :gender, :weight, :isDeleted, :created, :changed, :birthDate);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateUserParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public User update(User entity) throws NoSuchEntityException {
        if (entity.getId() == null) {
            throw new NoSuchEntityException("There is no id data for this user");
        }

        try {
            findOne(entity.getId());
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such user to update");
        }

        final String updateQuery = "update users set name=:name, surname=:surname, login=:login, gender=:gender, " +
                "weight=:weight, is_deleted=:isDeleted, created=:created, changed=:changed, birth_date=:birthDate where id=:id;";

        MapSqlParameterSource params = generateUserParamsMap(entity);
        params.addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(updateQuery, params);
        return findOne(entity.getId());
    }

    @Override
    public boolean delete(Long id) throws NoSuchEntityException {
        try {
            findOne(id);
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such user to delete");
        }

        final String deleteQuery = "delete from users where id =:id;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteQuery, params);
        return true;
    }

    @Override
    public List<User> findUsersByQuery(Integer limit, String query) {
        final String searchQuery = "select * from users where name like :query limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getUserRowMapper);
    }

    @Override
    public void batchInsert(List<User> users) {
        final String createQuery = "insert into users (name, surname, birth_date, login, weight) " +
                "values (:name, :surname, :birthDate, :login, :weight);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (User user : users) {
            batchParams.add(generateUserParamsMap(user));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private MapSqlParameterSource generateUserParamsMap(User entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("surname", entity.getSurname());
        params.addValue("login", entity.getLogin());
        params.addValue("gender", entity.getGender());
        params.addValue("weight", entity.getWeight());
        params.addValue("isDeleted", entity.getIsDeleted());
        params.addValue("created", entity.getCreated());
        params.addValue("changed", entity.getChanged());
        params.addValue("birthDate", entity.getBirthDate());

        return params;
    }

    private User getUserRowMapper(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(UserColumn.ID));
        user.setName(rs.getString(UserColumn.NAME));
        user.setSurname(rs.getString(UserColumn.SURNAME));
        user.setLogin(rs.getString(UserColumn.LOGIN));
        user.setGender(rs.getString(UserColumn.GENDER));
        user.setWeight(rs.getFloat(UserColumn.WEIGHT));
        user.setIsDeleted(rs.getBoolean(UserColumn.IS_DELETED));
        user.setCreated(rs.getDate(UserColumn.CREATED));
        user.setChanged(rs.getDate(UserColumn.CHANGED));
        user.setBirthDate(rs.getDate(UserColumn.BIRTH_DATE));

        return user;
    }
}
