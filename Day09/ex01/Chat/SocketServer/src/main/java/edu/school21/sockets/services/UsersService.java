package edu.school21.sockets.services;

public interface UsersService {
    boolean signUp(String username, String password);

    boolean signIn(String username, String password);
}
