package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;

public interface MessageRepository extends CrudRepository<Message> {
    String FIND_ALL_QUERY = "SELECT * FROM message;";
    String FIND_BY_ID_QUERY = "SELECT * FROM message WHERE id = ?;";
    String INSERT_QUERY = "INSERT INTO message(sender, message_text, " +
            "sending_time) VALUES (?, ?, ?);";
    String UPDATE_QUERY = "UPDATE message SET sender = ?, message_text = ?," +
            " sending_time = ? WHERE id = ?;";
    String DELETE_QUERY = "DELETE FROM message WHERE id = ?;";
}
