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
            JavaMailSender javaMailSender = mailConfig.javaMailSender();
            emailService.sendResetPasswordEmail(javaMailSender, user.getEmail(), resetToken);
        }
    }
}
