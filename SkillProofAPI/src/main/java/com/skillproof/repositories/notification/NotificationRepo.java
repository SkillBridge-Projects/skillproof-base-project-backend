package com.skillproof.repositories.notification;

import com.skillproof.model.entity.Notification;
import com.skillproof.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByMessage(String message);

    List<Notification> findByUser(User user);

    @Query("SELECT n FROM Notification n WHERE n.message = :message")
    List<Notification> getNotificationsByMessage(@Param("message") String message);
}
