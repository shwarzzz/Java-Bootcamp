package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component(value = "templateRepository")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String ERROR_MESSAGE_PREFIX = "User's ";
    private final String ERROR_MESSAGE_SUFFIX = " can't be null";

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(
            @Qualifier("driverManager") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        checkArgumentForNull(id, "id");
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_TEMPLATE,
                    (resultSet, rowNum) -> Optional.of(new User(
                            resultSet.getLong("id"),
                            resultSet.getString("email"),
                            resultSet.getString("password"))),
                    id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_TEMPLATE,
                    (resultSet, rowNum) ->
                            new User(
                                    resultSet.getLong("id"),
                                    resultSet.getString("email"),
                                    resultSet.getString("password"))
            );
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(User entity) {
        checkArgumentForNull(entity, "entity");
        if (entity.getId() == null) {
            jdbcTemplate.update(INSERT_WITHOUT_ID_GEN_TEMPLATE, entity.getId(),
                    entity.getEmail());
        } else {
            jdbcTemplate.update(INSERT_WITH_ID_GEN_TEMPLATE, entity.getId(),
                    entity.getEmail());
        }
    }

    @Override
    public void update(User entity) {
        checkArgumentForNull(entity, "entity");
        jdbcTemplate.update(UPDATE_TEMPLATE, entity.getEmail(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        checkArgumentForNull(id, "id");
        jdbcTemplate.update(DELETE_TEMPLATE, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        checkArgumentForNull(email, "email");
        try {
            return jdbcTemplate.queryForObject(FIND_BY_EMAIL_TEMPLATE,
                    (resultSet, rowNum) -> Optional.of(new User(
                            resultSet.getLong("id"),
                            resultSet.getString("email"))),
                    email);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void checkArgumentForNull(Object obj, String objectName) {
        if (obj == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_PREFIX + objectName +
                    ERROR_MESSAGE_SUFFIX);
        }
    }
}
