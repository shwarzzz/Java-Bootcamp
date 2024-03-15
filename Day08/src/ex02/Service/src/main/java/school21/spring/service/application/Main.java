package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersRepository repository = context
                .getBean("standardRepository", UsersRepository.class);
        System.out.println(repository.findAll());
        repository = context
                .getBean("templateRepository", UsersRepository.class);
        System.out.println(repository.findAll());
    }
}
