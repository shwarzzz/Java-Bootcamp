package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final BufferedReader consoleReader;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        consoleReader = new BufferedReader(
                new InputStreamReader(System.in));
    }

    public void start() throws IOException {
        try {
            String serverMessage = getMessage();
            System.out.println(serverMessage);
            sendConsoleMessage();
            serverMessage = getMessage();
            System.out.println(serverMessage);
            if (!serverMessage.equals("Wrong command! Bye!")) {
                sendConsoleMessage();
                System.out.println(getMessage());
                sendConsoleMessage();
                System.out.println(getMessage());
            }
        } finally {
            socket.close();
            out.close();
            in.close();
            consoleReader.close();
        }
    }

    private void sendConsoleMessage() throws IOException {
        String message = consoleReader.readLine();
        out.write(message + "\n");
        out.flush();
    }

    private String getMessage() throws IOException {
        return in.readLine();
    }
}
