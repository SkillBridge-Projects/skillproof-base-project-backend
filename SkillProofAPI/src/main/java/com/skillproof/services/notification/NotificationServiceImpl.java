package com.skillproof.services.notification;

import com.skillproof.enums.NotificationStatus;
import com.skillproof.model.entity.Notification;
import com.skillproof.model.entity.User;
import com.skillproof.repositories.notification.NotificationRepo;
import com.skillproof.repositories.user.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationRepo notificationRepo;
    private final UserRepository userRepository;

    public NotificationServiceImpl(JavaMailSender mailSender, NotificationRepo notificationRepo, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.notificationRepo = notificationRepo;
        this.userRepository = userRepository;
    }

    @Override
    public void sendNotification(String userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setStatus(NotificationStatus.UNREAD);
        notificationRepo.save(notification);
        sendEmail(user.getEmailAddress(), "Notification Alert", notification.getMessage());
    }

    private void sendEmail(String emailAddress, String notificationAlert, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject(notificationAlert);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Override
    public void updateNotificationStatus(Long notificationId, NotificationStatus status) throws Exception {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new  Exception("Notification not found for ID: " + notificationId));
        notification.setStatus(status);
        notificationRepo.save(notification);
    }
}