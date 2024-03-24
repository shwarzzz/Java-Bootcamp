package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository repository = context.getBean("usersRepositoryJdbsTemplateHds",
                UsersRepository.class);
        System.out.println(repository.findAll());
        repository = context.getBean("usersRepositoryJdbsDmds", UsersRepository.class);
        System.out.println(repository.findAll());
    }
}
