package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.repositories.UsersRepository;

public class UsersServiceImplTest {
    UsersService service;
    UsersRepository repository;

    @BeforeEach
    void init() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        service = context.getBean("usersServiceImpl", UsersService.class);
        repository = context.getBean("standardRepository", UsersRepository.class);
    }

    @ParameterizedTest(name = "Test {index} Hikari sign in - correct")
    @ValueSource(strings = {"test1@ya.ru", "test2@ya.ru",
            "test3@ya.ru", "test4@ya.ru", "test5@ya.ru"})
    void signInCorrect(String email) {
        String pass = service.signUp(email);
        Assertions.assertEquals(repository.findByEmail(email).get().getPassword(),
                pass);
    }
}
