package com.example.jobsearch.dao.impl;

import com.example.jobsearch.dao.VacancyDao;
import com.example.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                " exp_from, exp_to, as_active, employer_id, " +
                "created_date, update_date) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isAsActive(),
                vacancy.getEmployerId(),
                vacancy.getCreatedDate(),
                vacancy.getUpdateDate()
        );
    }


    @Override
    public Optional<Vacancy> findVacancyById(int id) {
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
                    UPDATE vacancy 
                    SET title = ?,    description = ?,     category_id = ?, 
                    salary = ?,     exp_from = ?,    exp_to = ?,    as_active = ?,    employer_id = ?,    update_date = ?
                    WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isAsActive(),
                vacancy.getEmployerId(),
                vacancy.getUpdateDate(),
                vacancy.getId() // переместили id в конец — для WHERE
        );
    }


    @Override
    public void deleteVacancyById(int id) {
        String sql = "DELETE FROM vacancy WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
