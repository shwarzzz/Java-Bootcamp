package school21.spring.service.repositories;

import school21.spring.service.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    String FIND_BY_ID_TEMPLATE = "SELECT * FROM \"user\" WHERE id = ?;";

    String FIND_ALL_TEMPLATE = "SELECT * FROM \"user\";";

    String INSERT_WITH_ID_GEN_TEMPLATE = "INSERT INTO \"user\" " +
            "(email, password) VALUES (?, ?);";

    String INSERT_WITHOUT_ID_GEN_TEMPLATE = "INSERT INTO \"user\" " +
            "VALUES (?, ?, ?);";

    String UPDATE_TEMPLATE = "UPDATE \"user\" SET email = ? WHERE id = ?;";

    String DELETE_TEMPLATE = "DELETE FROM \"user\" WHERE id = ?;";

    String FIND_BY_EMAIL_TEMPLATE = "SELECT * FROM \"user\" WHERE email = ?;";

    Optional<User> findByEmail(String email);
}
