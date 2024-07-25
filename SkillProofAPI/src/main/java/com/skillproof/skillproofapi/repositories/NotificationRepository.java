package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>  {

    @Query("SELECT n FROM Notification n WHERE n.connectionRequest.id  = :id ")
    Optional<Notification> findByConnectionId(@PathVariable Long id);
}
