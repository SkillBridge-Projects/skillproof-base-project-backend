package com.skillproof.services.notification;


import com.skillproof.model.request.notification.CreateNotificationRequest;
import com.skillproof.model.request.notification.NotificationResponse;
import com.skillproof.model.request.notification.UpdateNotificationRequest;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> listAllNotifications();

    NotificationResponse createNotification(CreateNotificationRequest createNotificationRequest);

    List<NotificationResponse> listNotificationsForUser(String userId);

    NotificationResponse updateNotification(Long id, UpdateNotificationRequest updateNotificationRequest);

    NotificationResponse getNotificationById(Long id);

    void deleteNotificationById(Long id);
}
