package app;

import app.controllers.UserController;
import app.models.User;
import app.repositories.LobbiesRepository;
import app.repositories.UsersRepository;
import app.services.EmailService;
import app.services.MailConfig;
import app.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UnitTestCalvin {


    private final WebClient client = WebClient.create();

    @Autowired
    private LobbiesRepository lobbiesRepository;
    @Autowired
    private UserController userController;

    @Autowired
    private MailConfig mailConfig;
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UsersRepository usersRepo;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void repoResetPassword() {
        // Create a test user with an initial password
        User user = new User();
        user.setId(1L);
        user.setPassword(passwordEncoder.encode("password123"));

        // Save the user to the repository
        usersRepo.save(user);

        // Define the new password
        String newPassword = "newPassword456";

        // Update the password directly using the setter
        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = usersRepo.save(user);

        // Verify the updated password
        assertNotNull(updatedUser, "User should not be null");
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()), "Password should be updated");
    }

    @Test
    public void findById(){

    }
}