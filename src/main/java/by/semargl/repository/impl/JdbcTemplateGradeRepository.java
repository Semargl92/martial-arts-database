package by.semargl.repository.impl;

import by.semargl.domain.Grade;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.GradeColumn;
import by.semargl.repository.GradeRepository;
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

public class JdbcTemplateGradeRepository implements GradeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateGradeRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Grade> findAll() {
        return jdbcTemplate.query("select * from grades order by id", this::getGradeRowMapper);
    }

    @Override
    public Grade findOne(Long id) throws NoSuchEntityException {
        Grade chosenOne;
        try {
            final String findOneWithNameParam = "select * from grades where id = :number";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("number", id);

            chosenOne = namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getGradeRowMapper);
        } catch (EmptyResultDataAccessException empty) {
            throw new NoSuchEntityException("There is no such grade");
        }
        return chosenOne;
    }

    @Override
    public Grade save(Grade entity) {
        final String createQuery = "insert into grades (name, duration_for_next_lvl, number_of_trainings_days, exam_type," +
                " martial_art_id) values (:name, :durationForNextLvl, :numberOfTrainingsDays, :examType, :martialArtId);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateGradeParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdGradeId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdGradeId);
    }

    @Override
    public Grade update(Grade entity) throws NoSuchEntityException {
        if (entity.getId() == null) {
            throw new NoSuchEntityException("There is no id data for this grade");
        }

        try {
            findOne(entity.getId());
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such grade to update");
        }

        final String updateQuery = "update grades set name=:name, duration_for_next_lvl=:durationForNextLvl, " +
                "number_of_trainings_days=:numberOfTrainingsDays, exam_type=:examType, martial_art_id=:martialArtId " +
                "where id=:id;";

        MapSqlParameterSource params = generateGradeParamsMap(entity);
        params.addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(updateQuery, params);
        return findOne(entity.getId());
    }

    @Override
    public boolean delete(Long id) throws NoSuchEntityException {
        try {
            findOne(id);
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such grade to delete");
        }

        final String deleteQuery = "delete from grades where id =:id;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteQuery, params);
        return true;
    }

    @Override
    public List<Grade> findGradeByQuery(Integer limit, String query) {
        final String searchQuery = "select * from grades where name like :query limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getGradeRowMapper);
    }

    @Override
    public void batchInsert(List<Grade> grades) {
        final String createQuery = "insert into grades (name, duration_for_next_lvl, number_of_trainings_days, exam_type," +
                " martial_art_id) values (:name, :durationForNextLvl, :numberOfTrainingsDays, :examType, :martialArtId);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Grade grade : grades) {
            batchParams.add(generateGradeParamsMap(grade));
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private MapSqlParameterSource generateGradeParamsMap(Grade entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("durationForNextLvl", entity.getDurationForNextLvl());
        params.addValue("numberOfTrainingsDays", entity.getNumberOfTrainingsDays());
        params.addValue("examType", entity.getExamType());
        params.addValue("martialArtId", entity.getMartialArtId());

        return params;
    }

    private Grade getGradeRowMapper(ResultSet rs, int i) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getLong(GradeColumn.ID));
        grade.setName(rs.getString(GradeColumn.NAME));
        grade.setDurationForNextLvl(rs.getInt(GradeColumn.DURATION_FOR_NEXT_LVL));
        grade.setNumberOfTrainingsDays(rs.getInt(GradeColumn.NUMBER_OF_TRAININGS_DAYS));
        grade.setExamType(rs.getString(GradeColumn.EXAM_TYPE));
        grade.setMartialArtId(rs.getLong(GradeColumn.MARTIAL_ART_ID));

        return grade;
    }
}
