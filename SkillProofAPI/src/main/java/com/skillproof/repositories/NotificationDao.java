package com.skillproof.repositories;

import com.skillproof.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, Long> {

    List<Notification> findByFollowerId(String followerId);
}
