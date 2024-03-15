package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.EntityDoesNotExistException;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Collections;
import java.util.Optional;

public class Program {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/java_test_db";
    public static final String DB_USERNAME = "tester";
    public static final String DB_PASSWORD = "1234";
    public static final HikariDataSource dataSource = new HikariDataSource();

    public static void main(String[] args) {
        setDataSourceConfiguration();
        testFullUpdate();
        testExceptions();
        dataSource.close();
    }

    public static void testFullUpdate() {
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> messageOptional = messagesRepository.findById(5L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            User tmp = new User(1L, "upd", "pass",
                    Collections.emptyList(), Collections.emptyList());
            message.setAuthor(tmp);
            message.setChatroom(new Chatroom(3L, "upd", tmp,
                    Collections.emptyList()));
            message.setText("Updated Text");
            message.setDate(null);
            messagesRepository.update(message);
            System.out.println("Test 1: Full update");
        }
    }

    public static void testExceptions() {
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        Message message = messagesRepository.findById(5L).get();
        User tmp = new User(10L, "upd", "pass",
                Collections.emptyList(), Collections.emptyList());
        message.setAuthor(tmp);
        message.setChatroom(new Chatroom(3L, "upd", tmp,
                Collections.emptyList()));
        message.setText("Updated Text");
        message.setDate(null);
        catchException(messagesRepository, message, 2);
        tmp = new User(null, "upd", "pass",
                Collections.emptyList(), Collections.emptyList());
        message.setAuthor(tmp);
        catchException(messagesRepository, message, 3);
        tmp = new User(1L, "upd", "pass",
                Collections.emptyList(), Collections.emptyList());
        message.setAuthor(tmp);
        message.setChatroom(new Chatroom(1523L, "upd", tmp,
                Collections.emptyList()));
        catchException(messagesRepository, message, 4);
        message.setChatroom(new Chatroom(null, "upd", tmp,
                Collections.emptyList()));
        catchException(messagesRepository, message, 5);
        message.setAuthor(null);
        catchException(messagesRepository, message, 6);
        message.setAuthor(new User(1L, "upd", "pass",
                Collections.emptyList(), Collections.emptyList()));
        message.setChatroom(null);
        catchException(messagesRepository, message, 7);
        catchException(messagesRepository, new Message(123L, null,
                null, null, null), 8);
        catchException(messagesRepository, null, 9);
        catchException(messagesRepository, new Message(null, null,
                null, null, null), 10);
    }

    public static void setDataSourceConfiguration() {
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
    }

    public static void catchException(MessagesRepository messagesRepository,
                                      Message message, int testNumber) {
        try {
            messagesRepository.update(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println("Test " + testNumber +
                    ": NotSavedSubEntityException caught: " + e.getMessage());
        } catch (EntityDoesNotExistException e) {

            System.out.println("Test " + testNumber +
                    ": EntityDoesNotExistException caught: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Test " + testNumber +
                    ": IllegalArgumentException caught: " + e.getMessage());
        }
    }
}
