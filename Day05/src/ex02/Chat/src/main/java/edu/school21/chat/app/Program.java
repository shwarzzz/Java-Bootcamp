package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Program {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/java_test_db";
    public static final String DB_USERNAME = "tester";
    public static final String DB_PASSWORD = "1234";
    public static final HikariDataSource dataSource = new HikariDataSource();

    public static void main(String[] args) {
        setDataSourceConfiguration();
        testGenInsert();
        testThrowException();
        testWithoutGenInsert();
        dataSource.close();
    }

    public static void testGenInsert() {
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        User creator = new User(1L, "user", "pass",
                new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(5L, "room",
                creator, new ArrayList<>());
        Message message = new Message(null, creator, room,
                "Hello!", Timestamp.valueOf(LocalDateTime.now()));
        messagesRepository.save(message);
        System.out.println("Test 0: Generated id = " + message.getId()); // 6
    }

    public static void testThrowException() {
        try {
            MessagesRepository messagesRepository =
                    new MessagesRepositoryJdbcImpl(dataSource);
            messagesRepository.save(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1: Illegal argument caught");
        }
        try {
            MessagesRepository messagesRepository =
                    new MessagesRepositoryJdbcImpl(dataSource);
            User creator = new User(1L, "user", "pass",
                    new ArrayList<>(), new ArrayList<>());
            Chatroom room = new Chatroom(15L, "room",
                    creator, new ArrayList<>());
            Message message = new Message(null, creator, room,
                    "Hello!", Timestamp.valueOf(LocalDateTime.now()));
            messagesRepository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println("Test 2: Custom exception caught");
        }
        try {
            MessagesRepository messagesRepository =
                    new MessagesRepositoryJdbcImpl(dataSource);
            User creator = new User(3L, "user", "pass",
                    new ArrayList<>(), new ArrayList<>());
            Chatroom room = new Chatroom(null, "room",
                    creator, new ArrayList<>());
            Message message = new Message(null, creator, room,
                    "Hello!", Timestamp.valueOf(LocalDateTime.now()));
            messagesRepository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println("Test 3: Custom exception caught");
        }
    }

    public static void testWithoutGenInsert() {
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        User creator = new User(3L, "test4", "pass",
                new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(2L, "room",
                creator, new ArrayList<>());
        Message message = new Message(8L, creator, room,
                "Test without gen!", Timestamp.valueOf(LocalDateTime.now()));
        messagesRepository.save(message);
        System.out.println("Test 4: Message id = " + message.getId());
    }

    public static void setDataSourceConfiguration() {
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
    }
}