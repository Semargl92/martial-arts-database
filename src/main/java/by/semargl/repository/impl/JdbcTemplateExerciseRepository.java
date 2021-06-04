package by.semargl.repository.impl;

import by.semargl.domain.Exercise;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.ExerciseColumn;
import by.semargl.repository.ExerciseRepository;
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

public class JdbcTemplateExerciseRepository implements ExerciseRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateExerciseRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Exercise> findAll() {
        return jdbcTemplate.query("select * from exercises order by id", this::getExerciseRowMapper);
    }

    @Override
    public Exercise findOne(Long id) throws NoSuchEntityException {
        Exercise chosenOne;
        try {
            final String findOneWithNameParam = "select * from exercises where id = :number";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("number", id);

            chosenOne = namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getExerciseRowMapper);
        } catch (EmptyResultDataAccessException empty) {
            throw new NoSuchEntityException("There is no such exercise");
        }
        return chosenOne;
    }

    @Override
    public Exercise save(Exercise entity) {
        final String createQuery = "insert into exercises (name, grade_id, description, resources, type, is_weapon_technik) " +
                "values (:name, :gradeId, :description, :resources, :type, :isWeaponTechnik);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateExerciseParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdGradeId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdGradeId);
    }

    @Override
    public Exercise update(Exercise entity) throws NoSuchEntityException {
        if (entity.getId() == null) {
            throw new NoSuchEntityException("There is no id data for this exercise");
        }

        try {
            findOne(entity.getId());
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such exercise to update");
        }

        final String updateQuery = "update exercises set name=:name, grade_id=:gradeId, description=:description, " +
                "resources=:resources, type=:type, is_weapon_technik=:isWeaponTechnik where id=:id;";

        MapSqlParameterSource params = generateExerciseParamsMap(entity);
        params.addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(updateQuery, params);
        return findOne(entity.getId());
    }

    @Override
    public boolean delete(Long id) throws NoSuchEntityException {
        try {
            findOne(id);
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such exercise to delete");
        }

        final String deleteQuery = "delete from exercises where id =:id;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteQuery, params);
        return true;
    }

    @Override
    public List<Exercise> findExerciseByQuery(Integer limit, String query) {
        final String searchQuery = "select * from exercises where name like :query limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getExerciseRowMapper);
    }

    @Override
    public void batchInsert(List<Exercise> exercises) {
        final String createQuery = "insert into exercises (name, grade_id, description, resources, type, is_weapon_technik) " +
                "values (:name, :gradeId, :description, :resources, :type, :isWeaponTechnik);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Exercise exercise : exercises) {
            batchParams.add(generateExerciseParamsMap(exercise));
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private MapSqlParameterSource generateExerciseParamsMap(Exercise entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("gradeId", entity.getGradeId());
        params.addValue("description", entity.getDescription());
        params.addValue("resources", entity.getResources());
        params.addValue("type", entity.getType());
        params.addValue("isWeaponTechnik", entity.getIsWeaponTechnik());

        return params;
    }

    private Exercise getExerciseRowMapper(ResultSet rs, int i) throws SQLException {
        Exercise exercise = new Exercise();
        exercise.setId(rs.getLong(ExerciseColumn.ID));
        exercise.setName(rs.getString(ExerciseColumn.NAME));
        exercise.setGradeId(rs.getLong(ExerciseColumn.GRADE_ID));
        exercise.setDescription(rs.getString(ExerciseColumn.DESCRIPTION));
        exercise.setResources(rs.getString(ExerciseColumn.RESOURCES));
        exercise.setType(rs.getString(ExerciseColumn.TYPE));
        exercise.setIsWeaponTechnik(rs.getBoolean(ExerciseColumn.IS_WEAPON_TECHNIK));

        return exercise;
    }
}
