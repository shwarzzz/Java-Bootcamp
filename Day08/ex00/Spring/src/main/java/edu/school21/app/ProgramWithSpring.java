package edu.school21.app;

import edu.school21.printers.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProgramWithSpring {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");
        Printer printer = context.getBean("printerUpperErrWithPrefix",
                Printer.class);
        printer.print("Hello!");
    }
}
