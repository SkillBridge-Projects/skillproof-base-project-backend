package com.skillproof.skillproofapi.repositories.notification;

import com.skillproof.skillproofapi.model.entity.Notification;
import com.skillproof.skillproofapi.repositories.NotificationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationRepositoryImpl.class);

    private final NotificationDao notificationDao;


    public NotificationRepositoryImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public Notification createNotification(Notification notificationEntity) {
        return notificationDao.saveAndFlush(notificationEntity);
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        return notificationDao.findNotificationByUserId(userId);
    }
}
