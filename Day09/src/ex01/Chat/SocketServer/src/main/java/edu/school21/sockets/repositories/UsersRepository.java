package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    String FIND_BY_ID_QUERY = "SELECT * FROM \"user\" WHERE id = ?;";
    String FIND_ALL_QUERY = "SELECT * FROM \"user\";";
    String INSERT_QUERY = "INSERT INTO \"user\"(name, password) VALUES(?, ?);";
    String UPDATE_QUERY = "UPDATE \"user\" SET name = ?," +
            " password = ? WHERE id = ?;";
    String DELETE_QUERY = "DELETE FROM \"user\" WHERE id = ?;";
    String FIND_BY_NAME_QUERY = "SELECT * FROM \"user\" WHERE name = ?;";

    Optional<User> findByName(String username);
}
