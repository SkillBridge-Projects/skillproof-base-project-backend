package com.skillproof.repositories.notification;

import com.skillproof.model.entity.Notification;
import com.skillproof.repositories.NotificationDao;
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
    public Notification createNotification(Notification notification) {
        return notificationDao.saveAndFlush(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationDao.findAll();
    }

    @Override
    public List<Notification> listNotificationsForUser(String userId) {
        return notificationDao.findByUserId(userId);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationDao.findById(id).orElse(null);
    }

    @Override
    public Notification updateConnection(Notification notification) {
        return notificationDao.saveAndFlush(notification);
    }
}
