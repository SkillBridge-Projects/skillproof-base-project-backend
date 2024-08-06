package com.skillproof.services.email;

import com.skillproof.configuration.AppConfig;
import com.skillproof.utils.EmailTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final AppConfig appConfig;

    public EmailServiceImpl(JavaMailSender javaMailSender, AppConfig appConfig) {
        this.javaMailSender = javaMailSender;
        this.appConfig = appConfig;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(appConfig.getFrom());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    // Send invitation email
    @Override
    public void sendInvitationEmail(String to, String recipientName) {
        String subject = "Invitation to Join Our Platform";
        String content = EmailTemplate.getInvitationEmail(recipientName, appConfig.getInvitationLink());
        sendEmail(to, subject, content);
    }

    // Send user creation success email
    public void sendUserCreationEmail(String to, String recipientName) {
        String subject = "Account Successfully Created";
        String content = EmailTemplate.getUserCreationEmail(recipientName);
        sendEmail(to, subject, content);
    }

    // Send password reset email
    public void sendPasswordResetEmail(String to, String recipientName, String resetLink) {
        String subject = "Password Reset Request";
        String content = EmailTemplate.getPasswordResetEmail(recipientName, resetLink);
        sendEmail(to, subject, content);
    }

    // Send generic notification email
    public void sendNotificationEmail(String to, String recipientName, String notificationContent) {
        String subject = "Notification";
        String content = EmailTemplate.getNotificationEmail(recipientName, notificationContent);
        sendEmail(to, subject, content);
    }
}
