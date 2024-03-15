package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final String START_MESSAGE = "Hello from server!";
    private final String SIGN_UP_COMMAND = "signUp";
    private final ServerSocket serverSocket;
    private final UsersService service;
    private Socket clientSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        service = context.getBean("usersServiceImpl", UsersService.class);
    }

    public void start() throws IOException {
        try {
            clientSocket = serverSocket.accept();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(
                         new OutputStreamWriter(clientSocket.getOutputStream()))) {
                sendMessage(START_MESSAGE, out);
                String clientCommand = getMessage(in);
                if (clientCommand.equals(SIGN_UP_COMMAND)) {
                    registerNewUser(in, out);
                } else {
                    sendMessage("Wrong command! Bye!", out);
                }
            }
        } finally {
            serverSocket.close();
            clientSocket.close();
        }
    }


    private void sendMessage(String message, BufferedWriter out)
            throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    private String getMessage(BufferedReader in) throws IOException {
        return in.readLine();
    }

    private void registerNewUser(BufferedReader in,
                                 BufferedWriter out) throws IOException {
        sendMessage("Enter username:", out);
        String username = getMessage(in);
        sendMessage("Enter password:", out);
        String password = getMessage(in);
        if (service.signUp(username, password)) {
            sendMessage("Successful!", out);
        } else {
            sendMessage("User with username " + username
                    + " already exist!", out);
        }
    }
}