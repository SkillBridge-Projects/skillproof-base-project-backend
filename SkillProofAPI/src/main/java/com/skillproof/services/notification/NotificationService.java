package com.skillproof.services.notification;

import com.skillproof.enums.NotificationStatus;

public interface NotificationService {

    void sendNotification(String userId, String message) ;

    void updateNotificationStatus(Long notificationId, NotificationStatus status) throws Exception;

}