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
import com.skillproof.services.AWSS3Service;
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
    private final AWSS3Service awss3Service;

    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository,
                                   AWSS3Service awss3Service) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.awss3Service = awss3Service;
    }

    @Override
    public List<NotificationResponse> listAllNotifications() {
        LOG.debug("Start of listAllNotifications method.");
        return getNotificationResponseList(notificationRepository.getAllNotifications());
    }

    @Override
    public NotificationResponse createNotification(CreateNotificationRequest createNotificationRequest) {
        LOG.debug("Start of createNotification method.");
        User user = userRepository.getUserById(createNotificationRequest.getFollowerId());
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", createNotificationRequest.getFollowerId());
            throw new UserNotFoundException(ObjectConstants.USER, createNotificationRequest.getFollowerId());
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
    public NotificationResponse updateNotification(Long id, UpdateNotificationRequest updateNotificationRequest) {
        LOG.debug("Start of updateNotification method.");
        Notification notification = notificationRepository.getNotificationById(id);
        if (ObjectUtils.isEmpty(notification)) {
            LOG.error("Notification with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.NOTIFICATION, ObjectConstants.ID, id);
        }
        notification.setRead(updateNotificationRequest.isRead());
        notification = notificationRepository.updateConnection(notification);
        LOG.debug("End of updateNotification method.");
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

    @Override
    public void deleteNotificationById(Long id) {
        LOG.debug("Start of deleteNotificationById method.");
        Notification notification = notificationRepository.getNotificationById(id);
        if (ObjectUtils.isEmpty(notification)) {
            LOG.error("Notification with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.NOTIFICATION, ObjectConstants.ID, id);
        }
        notificationRepository.deleteNotification(id);
        LOG.debug("End of deleteNotificationById method.");
    }

    private NotificationResponse getNotificationResponse(Notification notification) {
        NotificationResponse notificationResponse = ResponseConverter
                .copyProperties(notification, NotificationResponse.class);
        notificationResponse.setFollowerId(notification.getFollower().getId());
        notificationResponse.setProfilePicture(awss3Service.getPresignedUrl(notification.getProfilePicture()));
        return notificationResponse;
    }

    private Notification createNotificationEntity(CreateNotificationRequest createNotificationRequest, User user) {
        LOG.debug("Start of createNotificationEntity method.");
        Notification notification = new Notification();
        notification.setFollower(user);
        notification.setRead(createNotificationRequest.isRead());
        notification.setNotificationType(createNotificationRequest.getNotificationType());
        notification.setMessage(createNotificationRequest.getMessage());
        notification.setProfilePicture(createNotificationRequest.getProfilePicture());
        notification.setFollowingId(createNotificationRequest.getFollowingId());
        notification.setFollowingUserName(createNotificationRequest.getFollowingUserName());
        LOG.debug("End of createNotificationEntity method.");
        return notification;
    }

    private List<NotificationResponse> getNotificationResponseList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::getNotificationResponse)
                .collect(Collectors.toList());
    }
}
