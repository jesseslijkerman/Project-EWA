package app.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailService {
    public void sendResetPasswordEmail(JavaMailSender mailSender, String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("ewateam5@outlook.com");
        message.setSubject("Password Reset");
        message.setText("To reset your password, click the following link: " +
                "https://ewafrontend.z6.web.core.windows.net/resetPassword/" + resetToken);

        mailSender.send(message);
    }

    public void sendGameInvite(JavaMailSender mailSender, String email, String inviter, Long matchId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("ewateam5@outlook.com");
        message.setSubject(inviter + " has invited you to a game!");
        message.setText("Click the following link to join: https://ewafrontend.z6.web.core.windows.net/match/" +
                matchId);

        mailSender.send(message);
    }
}
