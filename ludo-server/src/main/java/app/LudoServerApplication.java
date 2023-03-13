package app;

import app.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
public class LudoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LudoServerApplication.class, args);
    }

}
