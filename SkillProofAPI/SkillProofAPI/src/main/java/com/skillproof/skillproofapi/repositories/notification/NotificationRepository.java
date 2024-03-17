package com.skillproof.skillproofapi.repositories.notification;

import com.skillproof.skillproofapi.model.entity.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification createNotification(Notification notificationEntity);

    List<Notification> getNotifications(Long userId);
}
