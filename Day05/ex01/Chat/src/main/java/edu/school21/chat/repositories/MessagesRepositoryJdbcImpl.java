package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final HikariDataSource dataSource;

    public MessagesRepositoryJdbcImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Message's id can't be null");
        }
        Optional<Message> result = Optional.empty();
        String query = "SELECT message.id AS message_id, text, date, cu.id AS " +
                "user_id, login, password, cr.id AS chatroom_id, name " +
                "FROM message JOIN chat_user cu on author_id = cu.id  " +
                "JOIN chatroom cr on chatroom_id = cr.id WHERE message.id = ?;";
        try (Connection connection = getNewConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet queryResult = statement.getResultSet();
            if (queryResult.next()) {
                User author = new User(queryResult.getLong("user_id"),
                        queryResult.getString("login"),
                        queryResult.getString("password"),
                        null,
                        null);
                Chatroom chatroom = new Chatroom(
                        queryResult.getLong("chatroom_id"),
                        queryResult.getString("name"),
                        null,
                        null);
                result = Optional.of(new Message(
                        queryResult.getLong("message_id"),
                        author,
                        chatroom,
                        queryResult.getString("text"),
                        queryResult.getTimestamp("date")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return result;
    }

    private Connection getNewConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
