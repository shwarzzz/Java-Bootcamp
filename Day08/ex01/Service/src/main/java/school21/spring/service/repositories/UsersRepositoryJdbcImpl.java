package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final String ERROR_MESSAGE_PREFIX = "User's ";
    private final String ERROR_MESSAGE_SUFFIX = " can't be null";
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        checkArgumentForNull(id, "id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_BY_ID_TEMPLATE)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_TEMPLATE);
            if (resultSet.isBeforeFirst()) {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    User foundUser = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("email"));
                    result.add(foundUser);
                }
                return result;
            } else {
                return Collections.emptyList();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User entity) {
        checkArgumentForNull(entity, "entity");
        checkArgumentForNull(entity.getId(), "id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_TEMPLATE)) {
            if (!findById(entity.getId()).equals(Optional.empty())) {
                throw new IllegalArgumentException("Entity with id " +
                        entity.getId() + " already exist");
            }
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        checkArgumentForNull(entity, "entity");
        checkArgumentForNull(entity.getId(), "id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_TEMPLATE)) {
            if (findById(entity.getId()).equals(Optional.empty())) {
                throw new IllegalArgumentException("Entity with id " +
                        entity.getId() + " does not exist");
            }
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        checkArgumentForNull(id, "id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_TEMPLATE)) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        checkArgumentForNull(email, "email");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(FIND_BY_EMAIL_TEMPLATE)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkArgumentForNull(Object obj, String objectName) {
        if (obj == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_PREFIX +
                    objectName + ERROR_MESSAGE_SUFFIX);
        }
    }
}
