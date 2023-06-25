package app.services;

import app.models.User;
import app.repositories.UsersRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;
    private final EmailService emailService;
    private final MailConfig mailConfig;

    public UserService(UsersRepository userRepository, EmailService emailService, MailConfig mailConfig) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.mailConfig = mailConfig;
    }

    public String generateRandomToken(){
        String token = RandomString.make(45);
        return token;
    }

    public void generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            String resetToken = generateRandomToken();
            user.setResetPasswordToken(resetToken);
            userRepository.save(user);

            /**
             * These 2 lines are needed to send emails. JavaMailSender retrieves the configuration from the MailConfig.java
             * The emailService is used to create the emails and sends the emails.
             */
            JavaMailSender javaMailSender = mailConfig.javaMailSender();
            emailService.sendResetPasswordEmail(javaMailSender, user.getEmail(), resetToken);
        }
    }
}
