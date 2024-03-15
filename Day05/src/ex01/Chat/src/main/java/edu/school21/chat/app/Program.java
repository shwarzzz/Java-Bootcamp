package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/java_test_db";
    public static final String DB_USERNAME = "tester";
    public static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        System.out.println("Enter a message ID: ");
        try (Scanner inp = new Scanner(System.in);
             HikariDataSource dataSource = new HikariDataSource()) {
            Long id = inp.nextLong();
            setDataSourceConfiguration(dataSource);
            MessagesRepositoryJdbcImpl messagesRepository
                    = new MessagesRepositoryJdbcImpl(dataSource);
            printQueryResult(messagesRepository.findById(id), id);
        } catch (InputMismatchException e) {
            System.out.println("Wrong id format");
            System.exit(-1);
        }
    }

    public static void setDataSourceConfiguration(HikariDataSource ds) {
        ds.setJdbcUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
    }

    public static void printQueryResult(Optional<Message> message, Long id) {
        if (message.isPresent()) {
            System.out.println(message.get());
        } else {
            System.out.println("Message with id = " + id + " does not exit");
        }
    }
}
