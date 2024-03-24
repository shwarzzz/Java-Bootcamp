package edu.school21.sockets.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReaderThread extends Thread {
    private final Socket sender;
    private final BufferedReader in;

    public MessageReaderThread(Socket sender) throws IOException {
        this.sender = sender;
        in = new BufferedReader(new InputStreamReader(sender.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String message = getMessage();
            while (!sender.isClosed() && message != null) {
                System.out.println(message);
                message = getMessage();
            }
        } catch (IOException ignored) {

        } finally {
            closeResources();
        }
    }

    private String getMessage() throws IOException {
        return in.readLine();
    }

    private void closeResources() {
        try {
            sender.close();
        } catch (IOException ignored) {

        }
    }
}