package com.skillproof.skillproofapi.services.notification;

import com.skillproof.skillproofapi.model.entity.Notification;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.services.user.UserServiceImpl;
import com.skillproof.skillproofapi.enumerations.NotificationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.skillproof.skillproofapi.enumerations.NotificationType.CONNECTION_REQUEST;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserServiceImpl userService;

    @Override
    public Set<Notification> getNotifications(Long userId) {
        Set<Notification> notificationsActive = new HashSet<>();
        User currentUser = userService.getUserById(userId);
        for(Notification not: currentUser.getNotifications()){
            if(!not.getIsShown() && not.getType() == NotificationType.COMMENT ) {
                notificationsActive.add(not);
            } else if (!not.getIsShown() && not.getType() == NotificationType.INTEREST) {
                notificationsActive.add(not);
            } else if (!not.getIsShown() && !not.getConnectionRequest().getIsAccepted() && not.getType() == CONNECTION_REQUEST) {
                notificationsActive.add(not);
            }
        }
        return notificationsActive;
    }
}
