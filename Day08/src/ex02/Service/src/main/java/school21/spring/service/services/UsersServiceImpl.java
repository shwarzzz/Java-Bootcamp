package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;

    @Autowired
    public UsersServiceImpl(
            @Qualifier("standardRepository") UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String email) {
        String password = UUID.randomUUID().toString();
        User newUser = new User(null, email);
        newUser.setPassword(password);
        repository.save(newUser);
        return password;
    }
}
