package com.skillproof.services.notification;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Notification;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.notification.CreateNotificationRequest;
import com.skillproof.model.request.notification.NotificationResponse;
import com.skillproof.model.request.notification.UpdateNotificationRequest;
import com.skillproof.repositories.notification.NotificationRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationResponse> listAllNotifications() {
        LOG.debug("Start of listAllNotifications method.");
        return getNotificationResponseList(notificationRepository.getAllNotifications());
    }

    @Override
    public NotificationResponse createNotification(CreateNotificationRequest createNotificationRequest) {
        LOG.debug("Start of createNotification method.");
        User user = userRepository.getUserById(createNotificationRequest.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", createNotificationRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createNotificationRequest.getUserId());
        }
        Notification notification = notificationRepository.createNotification(createNotificationEntity(createNotificationRequest, user));
        LOG.debug("End of createNotification method.");
        return getNotificationResponse(notification);
    }

    @Override
    public List<NotificationResponse> listNotificationsForUser(String userId) {
        LOG.debug("Start of listNotificationsForUser method.");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Notification> notifications = notificationRepository.listNotificationsForUser(userId);
        LOG.debug("End of listNotificationsForUser method.");
        return getNotificationResponseList(notifications);
    }

    @Override
    public NotificationResponse updateConnection(Long id, UpdateNotificationRequest updateNotificationRequest) {
        LOG.debug("Start of updateConnection method.");
        Notification notification = notificationRepository.getNotificationById(id);
        if (ObjectUtils.isEmpty(notification)) {
            LOG.error("Notification with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.NOTIFICATION, ObjectConstants.ID, id);
        }
        notification.setRead(updateNotificationRequest.isRead());
        notification = notificationRepository.updateConnection(notification);
        LOG.debug("End of updateConnection method.");
        return getNotificationResponse(notification);
    }

    @Override
    public NotificationResponse getNotificationById(Long id) {
        LOG.debug("Start of getNotificationById method.");
        Notification notification = notificationRepository.getNotificationById(id);
        if (ObjectUtils.isEmpty(notification)) {
            LOG.error("Notification with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.NOTIFICATION, ObjectConstants.ID, id);
        }
        return getNotificationResponse(notification);
    }

    private NotificationResponse getNotificationResponse(Notification notification) {
        NotificationResponse notificationResponse = ResponseConverter
                .copyProperties(notification, NotificationResponse.class);
        notificationResponse.setUserId(notification.getUser().getId());
        return notificationResponse;
    }

    private Notification createNotificationEntity(CreateNotificationRequest createNotificationRequest, User user) {
        LOG.debug("Start of createNotificationEntity method.");
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setRead(createNotificationRequest.isRead());
        LOG.debug("End of createNotificationEntity method.");
        return notification;
    }

    private List<NotificationResponse> getNotificationResponseList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::getNotificationResponse)
                .collect(Collectors.toList());
    }
}
