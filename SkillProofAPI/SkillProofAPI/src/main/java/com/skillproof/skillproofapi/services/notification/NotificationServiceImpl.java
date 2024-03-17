package com.skillproof.skillproofapi.services.notification;

import com.skillproof.skillproofapi.constants.ErrorMessageConstants;
import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
import com.skillproof.skillproofapi.model.entity.Connection;
import com.skillproof.skillproofapi.model.entity.Notification;
import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.connection.ConnectionResponse;
import com.skillproof.skillproofapi.model.request.notification.CreateNotificationRequest;
import com.skillproof.skillproofapi.model.request.notification.NotificationResponse;
import com.skillproof.skillproofapi.model.request.skillsAndExperience.SkillsAndExperienceResponse;
import com.skillproof.skillproofapi.repositories.notification.NotificationRepository;
import com.skillproof.skillproofapi.repositories.user.UserRepository;
import com.skillproof.skillproofapi.enumerations.NotificationType;
import com.skillproof.skillproofapi.utils.ResponseConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.skillproof.skillproofapi.enumerations.NotificationType.CONNECTION_REQUEST;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationResponse> listNotifications(Long userId) {
        User currentUser = userRepository.getUserById(userId);
        if (currentUser == null) {
            throw new UserNotFoundException(String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER, userId));
        }
        List<Notification> notifications = notificationRepository.getNotifications(userId);
        return getNotificationResponseList(notifications);
    }

    @Override
    public NotificationResponse createNotification(CreateNotificationRequest createNotificationRequest) {
        User user = userRepository.getUserById(createNotificationRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException(String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER,
                    createNotificationRequest.getUserId()));
        }
        Notification notification = notificationRepository.createNotification(createNotificationEntity(createNotificationRequest, user));
        return getNotificationResponse(notification);
    }

    private NotificationResponse getNotificationResponse(Notification notification) {
        NotificationResponse notificationResponse = ResponseConverter
                .copyProperties(notification, NotificationResponse.class);
        notificationResponse.setUserId(notification.getUser().getId());
        return notificationResponse;
    }

    private Notification createNotificationEntity(CreateNotificationRequest createNotificationRequest, User user) {
        Notification notification = new Notification();
        notification.setIsShown(createNotificationRequest.isShown());
        notification.setNotificationType(createNotificationRequest.getType());
        notification.setUser(user);
        notification.setCreatedDate(LocalDateTime.now());
        notification.setUpdatedDate(LocalDateTime.now());
        return notification;
    }

    private List<NotificationResponse> getNotificationResponseList(List<Notification> notifications){
        return notifications.stream()
                .map(entity -> {
                    NotificationResponse notificationResponse = ResponseConverter
                            .copyProperties(entity, NotificationResponse.class);
                    notificationResponse.setUserId(entity.getUser().getId());
                    return notificationResponse;
                })
                .collect(Collectors.toList());
    }
}
