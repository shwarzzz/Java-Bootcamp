package edu.school21.sockets.server;

import edu.school21.sockets.threads.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {

    public static final List<ClientThread> clients = new LinkedList<>();
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                try {
                    clients.add(new ClientThread(clientSocket));
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }


}