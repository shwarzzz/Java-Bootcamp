package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
    UsersServiceImpl service;
    @Mock
    UsersRepository repository;

    @BeforeEach
    void init() {
        service = new UsersServiceImpl(repository);
    }

    @Test
    void authenticateCorrectUserData() {
        String login = "correct";
        String password = "correct";
        User user = new User(1L, login,
                password, false);
        Mockito.when(repository.findByLogin(login)).thenReturn(user);
        assertTrue(service.authenticate(login, password));
        Mockito.verify(repository).update(user);
    }

    @Test
    void authenticateIncorrectPassword() {
        String login = "correct";
        User user = new User(1L, login, "correct",
                false);
        Mockito.when(repository.findByLogin(login)).thenReturn(user);
        assertFalse(service.authenticate(login, "incorrect"));
    }

    @Test
    void authenticateUserAlreadyLogIn() {
        String login = "correct";
        String password = "correct";
        User user = new User(1L, login, password, true);
        Mockito.when(repository.findByLogin(login)).thenReturn(user);
        assertThrows(AlreadyAuthenticatedException.class, () -> {
            service.authenticate(login, password);
        });
    }

    @Test
    void authenticateUserNotFound() {
        String login = "incorrect";
        String password = "incorrect";
        Mockito.when(repository.findByLogin(login))
                .thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> {
            service.authenticate(login, password);
        });
    }

    private static class EntityNotFoundException extends RuntimeException {
    }
}
