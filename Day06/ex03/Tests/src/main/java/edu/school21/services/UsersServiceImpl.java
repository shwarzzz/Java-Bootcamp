package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    boolean authenticate(String login, String password) {
        User user = repository.findByLogin(login);
        if (user.getAuthenticationStatus()) {
            throw new AlreadyAuthenticatedException("User already authenticated");
        }
        if (password.equals(user.getPassword())) {
            user.setAuthenticationStatus(true);
            repository.update(user);
            return true;
        }
        return false;

    }
}
