package app;

import app.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LudoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LudoServerApplication.class, args);
    }

}
