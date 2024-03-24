package edu.school21.sockets.threads;

import java.io.*;
import java.net.Socket;

public class MessageSenderThread extends Thread {
    private static final String END_MESSAGE = "Exit";
    private final Socket sender;
    private final BufferedReader console;
    private final BufferedWriter out;

    public MessageSenderThread(Socket sender) throws IOException {
        this.sender = sender;
        console = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
    }


    @Override
    public void run() {
        try {
            String message = "";
            while (!message.equals(END_MESSAGE) && !sender.isClosed()) {
                message = console.readLine();
                out.write(message + "\n");
                out.flush();
            }
        } catch (IOException ignored) {

        }
    }
}
