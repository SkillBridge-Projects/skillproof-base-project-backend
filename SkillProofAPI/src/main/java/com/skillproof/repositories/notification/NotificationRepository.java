package com.skillproof.repositories.notification;

import com.skillproof.model.entity.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification createNotification(Notification notification);

    List<Notification> getAllNotifications();

    List<Notification> listNotificationsForUser(String userId);

    Notification getNotificationById(Long id);

    Notification updateConnection(Notification notification);

    void deleteNotification(Long id);
}
