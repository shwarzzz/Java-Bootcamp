package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
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

    @Override
    public void save(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message entity can't be null");
        }
        validateMessageSubEntity(message);
        try (Connection connection = getNewConnection()) {
            if (!isUserEntityExist(connection, message.getAuthor().getId())) {
                throw new NotSavedSubEntityException("User with id = " + message
                        .getAuthor().getId() + " does not exist");
            }
            if (!isChatroomEntityExist(connection, message.getChatroom().getId())) {
                throw new NotSavedSubEntityException("Chatroom with id = " + message
                        .getChatroom().getId() + " does not exist");
            }
            if (message.getId() == null) {
                insertWithIdGen(connection, message);
            } else {
                insertWithoutIdGen(connection, message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private Connection getNewConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void validateMessageSubEntity(Message message) {
        if (message.getAuthor() == null) {
            throw new NotSavedSubEntityException("Author entity can't be null");
        }
        if (message.getChatroom() == null) {
            throw new NotSavedSubEntityException("Chatroom entity can't be null");
        }
        if (message.getAuthor().getId() == null) {
            throw new NotSavedSubEntityException("Author's id can't be null");
        }
        if (message.getChatroom().getId() == null) {
            throw new NotSavedSubEntityException("Chatroom's id can't be null");
        }
    }

    private boolean isEntityExist(Connection connection, String tableName,
                                  Long id) throws SQLException {
        try (PreparedStatement statement = connection
                .prepareStatement("SELECT id FROM " + tableName +
                        " WHERE id = ?;")) {
            statement.setLong(1, id);
            return statement.executeQuery().isBeforeFirst();
        }
    }

    private boolean isUserEntityExist(Connection connection, Long id)
            throws SQLException {
        return isEntityExist(connection, User.ENTITY_TABLE_NAME, id);
    }

    private boolean isChatroomEntityExist(Connection connection, Long id)
            throws SQLException {
        return isEntityExist(connection, Chatroom.ENTITY_TABLE_NAME, id);
    }

    private void insertWithIdGen(Connection connection, Message message)
            throws SQLException {
        try (PreparedStatement statement = connection
                .prepareStatement("INSERT INTO message (author_id, " +
                                "chatroom_id, text, date) VALUES (?, ?, ?, ?);",
                        new String[]{"id"})) {
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getChatroom().getId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, message.getDate());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            message.setId(resultSet.getLong("id"));
        }
    }

    private void insertWithoutIdGen(Connection connection, Message message)
            throws SQLException {
        try (PreparedStatement statement = connection
                .prepareStatement("INSERT INTO message " +
                        "(id, author_id, chatroom_id, text, date) " +
                        "VALUES (?, ?, ?, ?, ?);")) {
            statement.setLong(1, message.getId());
            statement.setLong(2, message.getAuthor().getId());
            statement.setLong(3, message.getChatroom().getId());
            statement.setString(4, message.getText());
            statement.setTimestamp(5, message.getDate());
            statement.executeUpdate();
        }
    }
}
