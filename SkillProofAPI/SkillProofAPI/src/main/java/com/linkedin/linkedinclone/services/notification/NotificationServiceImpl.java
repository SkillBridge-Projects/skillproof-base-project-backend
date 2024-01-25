package com.linkedin.linkedinclone.services.notification;

import com.linkedin.linkedinclone.model.Notification;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.services.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.linkedin.linkedinclone.enumerations.NotificationType.*;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserServiceImpl userService;

    @Override
    public Set<Notification> getNotifications(Long userId) {
        Set<Notification> notificationsActive = new HashSet<>();
        User currentUser = userService.getUserById(userId);
        for(Notification not: currentUser.getNotifications()){
            if(!not.getIsShown() && not.getType() == COMMENT ) {
                notificationsActive.add(not);
            } else if (!not.getIsShown() && not.getType() == INTEREST) {
                notificationsActive.add(not);
            } else if (!not.getIsShown() && !not.getConnection_request().getIsAccepted() && not.getType() == CONNECTION_REQUEST) {
                notificationsActive.add(not);
            }
        }
        return notificationsActive;
    }
}
