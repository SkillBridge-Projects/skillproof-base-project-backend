//package com.skillproof.skillproofapi.repositories;
//
//import com.skillproof.skillproofapi.model.entity.Notification;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface NotificationDao extends JpaRepository<Notification, Long>  {
//
//    @Query("SELECT n FROM Notification n WHERE n.connectionRequest.id  = :id ")
//    Optional<Notification> findByConnectionId(@PathVariable Long id);
//
//    @Query("SELECT n FROM Notification n INNER JOIN User u ON u.id = n.user.id WHERE u.id = :userId")
//    List<Notification> findNotificationByUserId(@PathVariable Long userId);
//}
