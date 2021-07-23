package by.semargl.repository.impl;

import by.semargl.domain.Student;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.StudentColumn;
import by.semargl.repository.StudentRepository;
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
public class JdbcTemplateStudentRepository implements StudentRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateStudentRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("select * from students order by id", this::getStudentRowMapper);
    }

    @Override
    public Student findOne(Long id) throws NoSuchEntityException {
        Student chosenOne;
        try {
            final String findOneWithNameParam = "select * from students where id = :number";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("number", id);

            chosenOne = namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getStudentRowMapper);
        } catch (EmptyResultDataAccessException empty) {
            throw new NoSuchEntityException("There is no such student");
        }
        return chosenOne;
    }

    @Override
    public Student save(Student entity) {
        final String createQuery = "insert into students (user_id, grade_id, teachers_user_id, last_exam_date, " +
                "numbers_of_trainings_days, start_date, is_deleted, created, changed) values (:userId, :gradeId, " +
                ":teachersUserId, :lastExamDate, :numbersOfTrainingsDays, :startDate, :isDeleted, :created, :changed);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        entity.setCreated(new Date(System.currentTimeMillis()));
        entity.setChanged(new Date(System.currentTimeMillis()));
        MapSqlParameterSource params = generateStudentParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public Student update(Student entity) throws NoSuchEntityException {
        if (entity.getId() == null) {
            throw new NoSuchEntityException("There is no id data for this student");
        }

        try {
            findOne(entity.getId());
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such student to update");
        }

        final String updateQuery = "update students set user_id=:userId, grade_id=:gradeId, teachers_user_id=:teachersUserId," +
                " last_exam_date=:lastExamDate, numbers_of_trainings_days=:numbersOfTrainingsDays, " +
                "start_date=:startDate, is_deleted=:isDeleted, changed=:changed where id=:id;";

        entity.setChanged(new Date(System.currentTimeMillis()));
        MapSqlParameterSource params = generateStudentParamsMap(entity);
        params.addValue("id", entity.getId());
        namedParameterJdbcTemplate.update(updateQuery, params);
        return findOne(entity.getId());
    }

    @Override
    public boolean delete(Long id) throws NoSuchEntityException {
        try {
            findOne(id);
        } catch (NoSuchEntityException ex) {
            throw new NoSuchEntityException("There is no such student to delete");
        }

        final String deleteQuery = "delete from students where id =:id;";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(deleteQuery, params);
        return true;
    }

    @Override
    public List<Student> findStudentsByGrade(Integer limit, Long grade) {
        final String searchQuery = "select * from students where grade_id = :grade limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("grade", grade);
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getStudentRowMapper);
    }

    @Override
    public void batchInsert(List<Student> students) {
        final String createQuery = "insert into students (user_id, grade_id, teachers_user_id, last_exam_date, " +
                "numbers_of_trainings_days, start_date, is_deleted, created, changed) values (:userId, :gradeId, " +
                ":teachersUserId, :lastExamDate, :numbersOfTrainingsDays, :startDate, :isDeleted, :created, :changed);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Student student : students) {
            student.setCreated(new Date(System.currentTimeMillis()));
            student.setChanged(new Date(System.currentTimeMillis()));
            batchParams.add(generateStudentParamsMap(student));
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private MapSqlParameterSource generateStudentParamsMap(Student entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", entity.getUserId());
        params.addValue("gradeId", entity.getGradeId());
        params.addValue("teachersUserId", entity.getTeachersUserId());
        params.addValue("lastExamDate", entity.getLastExamDate());
        params.addValue("numbersOfTrainingsDays", entity.getNumbersOfTrainingsDays());
        params.addValue("startDate", entity.getStartDate());
        params.addValue("isDeleted", entity.getIsDeleted());
        params.addValue("created", entity.getCreated());
        params.addValue("changed", entity.getChanged());

        return params;
    }

    private Student getStudentRowMapper(ResultSet rs, int i) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong(StudentColumn.ID));
        student.setUserId(rs.getLong(StudentColumn.USER_ID));
        student.setGradeId(rs.getLong(StudentColumn.GRADE_ID));
        student.setTeachersUserId(rs.getLong(StudentColumn.TEACHERS_USER_ID));
        student.setLastExamDate(rs.getDate(StudentColumn.LAST_EXAM_DATE));
        student.setNumbersOfTrainingsDays(rs.getInt(StudentColumn.NUMBERS_OF_TRAININGS_DAYS));
        student.setStartDate(rs.getDate(StudentColumn.START_DATE));
        student.setIsDeleted(rs.getBoolean(StudentColumn.IS_DELETED));
        student.setCreated(rs.getDate(StudentColumn.CREATED));
        student.setChanged(rs.getDate(StudentColumn.CHANGED));

        return student;
    }
}
