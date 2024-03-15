package edu.school21.sockets.threads;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ClientThread extends Thread {
    private final String START_MESSAGE = "Hello from server!";
    private final String SIGN_UP_COMMAND = "signUp";
    private final String SIGN_IN_COMMAND = "signIn";
    private final String EXIT_COMMAND = "Exit";
    private final Socket clientSocket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final UsersService service;
    private final MessageRepository messageRepository;
    private String clientUsername;

    public ClientThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        service = context.getBean("usersServiceImpl", UsersService.class);
        messageRepository = context.getBean("messageRepositoryImpl"
                , MessageRepository.class);
        this.start();
    }

    @Override
    public void run() {
        try {
            sendMessage(START_MESSAGE);
            if (!authorizeUser()) {
                return;
            }
            startMessaging();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources();
        }
    }

    private boolean authorizeUser() throws IOException {
        boolean isAuthorized = false;
        while (!isAuthorized) {
            String clientCommand = getMessage();
            if (clientCommand.equals(SIGN_UP_COMMAND)) {
                isAuthorized = signUp();
            } else if (clientCommand.equals(SIGN_IN_COMMAND)) {
                isAuthorized = signIn();
            } else if (clientCommand.equals(EXIT_COMMAND)) {
                return false;
            } else {
                sendMessage("Wrong input command! Try again!");
            }
        }
        return isAuthorized;
    }

    private void startMessaging() throws IOException {
        String sender = clientUsername + ": ";
        sendMessage("Start messaging");
        String message = getMessage();
        while (!message.equals(EXIT_COMMAND)) {
            messageRepository.save(new Message(clientUsername, message,
                    Timestamp.valueOf(LocalDateTime.now())));
            sendMessageToAll(sender + message);
            message = getMessage();
        }
        sendMessage("You have left the chat.");
    }

    public boolean isClosed() {
        return clientSocket.isClosed();
    }

    public boolean isLogIn() {
        return clientUsername != null;
    }

    public void sendMessage(String message)
            throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    private String getMessage() throws IOException {
        return in.readLine();
    }

    private boolean signUp() throws IOException {
        sendMessage("Enter username:");
        String username = getMessage();
        sendMessage("Enter password:");
        String password = getMessage();
        if (service.signUp(username, password)) {
            clientUsername = username;
            sendMessage("Successful!");
            return true;
        }
        sendMessage("User with username " + username
                + " already exist! Try again!");
        return false;
    }

    private boolean signIn() throws IOException {
        sendMessage("Enter username:");
        String username = getMessage();
        sendMessage("Enter password:");
        String password = getMessage();
        if (service.signIn(username, password)) {
            clientUsername = username;
            sendMessage("Hello, " + username + "! Welcome back!");
            return true;
        }
        sendMessage("Wrong username/password. Try again!");
        return false;
    }

    private void closeResources() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessageToAll(String message) throws IOException {
        for (ClientThread client : Server.clients) {
            if (client.isClosed() || !client.isLogIn()) {
                continue;
            }
            client.sendMessage(message);
        }
    }
}
