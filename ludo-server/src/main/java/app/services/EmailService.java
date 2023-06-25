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
                "http://localhost:5173/resetPassword/" + resetToken);

        mailSender.send(message);
    }

    public void sendGameInvite(JavaMailSender mailSender, String email, String inviter, Long matchId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("ewateam5@outlook.com");
        message.setSubject(inviter + " has invited you to a game!");
        message.setText("Click the following link to join: http://localhost:5173/match/" +
                matchId);

        mailSender.send(message);
    }
}
