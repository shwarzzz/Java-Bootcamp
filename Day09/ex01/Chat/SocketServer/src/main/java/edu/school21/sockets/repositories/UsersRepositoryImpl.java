package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        validateObject(id, "id");
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                    new UserMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_QUERY, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(User entity) {
        validateObject(entity, "entity");
        validateObject(entity.getName(), "name");
        validateObject(entity.getPassword(), "password");
        jdbcTemplate.update(INSERT_QUERY, entity.getName(),
                entity.getPassword());
    }

    @Override
    public void update(User entity) {
        validateObject(entity, "entity");
        validateObject(entity.getName(), "name");
        validateObject(entity.getPassword(), "password");
        jdbcTemplate.update(UPDATE_QUERY,
                entity.getName(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        validateObject(id, "id");
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public Optional<User> findByName(String username) {
        validateObject(username, "name");
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_NAME_QUERY,
                    new UserMapper(), username));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void validateObject(Object obj, String objectName) {
        if (obj == null) {
            throw new IllegalArgumentException("User's " + objectName
                    + " can't be null");
        }
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("password"));
        }
    }
}
