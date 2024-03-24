package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {
    public static final String FLAG = "--server-port";

    public static void main(String[] args) {
        int port = validateArguments(args);
        try {
            Client client = new Client(port);
            client.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int validateArguments(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong arguments number!");
        }
        String[] splitArg = args[0].split("=");
        if (splitArg.length != 2) {
            throw new IllegalArgumentException("Wrong argument!");
        }
        if (!splitArg[0].equals(FLAG)) {
            throw new IllegalArgumentException("Wrong flag!");
        }
        try {
            return Integer.parseInt(splitArg[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Flag value not a number!");
        }
    }
}
