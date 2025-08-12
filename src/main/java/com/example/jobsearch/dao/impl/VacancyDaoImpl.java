package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class VacancyDaoImpl implements VacancyDao {
    private final JdbcTemplate jdbcTemplate;

    public VacancyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveVacancy(Vacancy vacancy) {
        String sql = "INSERT INTO vacancy(title, description, category_id, salary," +
                " exp_from, exp_to, IS_ACTIVE, employer_id, " +
                "created_date, update_date) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vacancy.getTitle());
            ps.setString(2, vacancy.getDescription());
            ps.setLong(3, vacancy.getCategoryId());
            ps.setDouble(4, vacancy.getSalary());
            if (vacancy.getExpFrom() != null) {
                ps.setLong(5, vacancy.getExpFrom());
            } else {
                ps.setNull(5, Types.BIGINT);
            }

            if (vacancy.getExpTo() != null) {
                ps.setLong(6, vacancy.getExpTo());
            } else {
                ps.setNull(6, Types.BIGINT);
            }
            ps.setBoolean(7, vacancy.isActive());
            ps.setLong(8, vacancy.getEmployerId());
            if (vacancy.getCreatedDate() != null) {
                ps.setTimestamp(9, Timestamp.valueOf(vacancy.getCreatedDate()));
            } else {
                ps.setTimestamp(9, null);
            }

            if (vacancy.getUpdateDate() != null) {
                ps.setTimestamp(10, Timestamp.valueOf(vacancy.getUpdateDate()));
            } else {
                ps.setTimestamp(10, null);
            }
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            vacancy.setId(generatedId.longValue());
        }


    }


    @Override
    public Optional<Vacancy> findVacancyById(long id) {
        String sql = "SELECT * FROM vacancy WHERE id = ?";
        List<Vacancy> vacancies = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);
        return vacancies.stream().findFirst();
    }


    @Override
    public List<Vacancy> findAllVacancies() {
        String sql = "SELECT * FROM vacancy";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    @Override
    public List<Vacancy> findByUserId(int userId) {
        String sql = "SELECT * FROM vacancy WHERE id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }


    @Override
    public void updateVacancy(Vacancy vacancy) {
        String sql = """
                     UPDATE vacancy\s
                     SET title = ?,    description = ?,     category_id = ?,\s
                     salary = ?,     exp_from = ?,    exp_to = ?,    IS_ACTIVE = ?,    employer_id = ?,    update_date = ?
                     WHERE id = ?
                \s""";

        jdbcTemplate.update(sql,
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isActive(),
                vacancy.getEmployerId(),
                vacancy.getUpdateDate(),
                vacancy.getId()
        );
    }


    @Override
    public void deleteVacancyById(long id) {
        String sql = "DELETE FROM vacancy WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
