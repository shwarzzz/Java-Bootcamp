package edu.school21.sockets.client;

import edu.school21.sockets.threads.MessageReaderThread;
import edu.school21.sockets.threads.MessageSenderThread;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final Socket socket;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
    }

    public void start() throws IOException {
        MessageSenderThread senderThread = new MessageSenderThread(socket);
        MessageReaderThread readerThread = new MessageReaderThread(socket);
        senderThread.setDaemon(true);
        readerThread.setDaemon(true);
        try {
            readerThread.start();
            senderThread.start();
            senderThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            readerThread.interrupt();
            senderThread.interrupt();
            System.out.println(e.getMessage());
        } finally {
            socket.close();
        }
    }
}
