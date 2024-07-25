package com.skillproof.skillproofapi.services.notification;

import com.skillproof.skillproofapi.model.entity.Notification;

import java.util.Set;

public interface NotificationService {

    Set<Notification> getNotifications(Long userId);
}
