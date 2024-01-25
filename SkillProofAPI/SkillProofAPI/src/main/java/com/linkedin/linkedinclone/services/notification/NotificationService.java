package com.linkedin.linkedinclone.services.notification;

import com.linkedin.linkedinclone.model.Notification;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

public interface NotificationService {

    Set<Notification> getNotifications(Long userId);
}
