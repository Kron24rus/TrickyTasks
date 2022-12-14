package ru.mikheev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.mikheev.services.TransactionalService;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
//        context.getBean(NotLazyCaller.class).callLazy();
        context.getBean(TransactionalService.class).transactionBoth();
    }

}
