//package com.skillproof.skillproofapi.model.entity;
//
//import com.skillproof.skillproofapi.enumerations.NotificationType;
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "notification")
//public class Notification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "isShown")
//    private Boolean isShown;
//
//    @Column(name = "notification_type", nullable = false)
//    @Enumerated
//    @NonNull
//    private NotificationType notificationType;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id", referencedColumnName = "id")
////    private User user;
//
//    @OneToOne
//    @JoinColumn(name = "connection_request_id", referencedColumnName = "id")
//    private Connection connectionRequest;
//
//    @OneToOne
//    @JoinColumn(name = "new_comment_id", referencedColumnName = "id")
//    private Comment newComment;
//
//    @OneToOne
//    @JoinColumn(name = "new_interest_id", referencedColumnName = "id")
//    private InterestReaction newInterest;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//
//    public Notification(@NonNull NotificationType notificationType, User user, Connection connectionRequest) {
//        this.notificationType = notificationType;
////        this.user = user;
//        this.connectionRequest = connectionRequest;
//    }
//
//    public Notification(@NonNull NotificationType notificationType, User user, Comment newComment) {
//        this.notificationType = notificationType;
////        this.user = user;
//        this.newComment = newComment;
//    }
//
//    public Notification(@NonNull NotificationType notificationType, User user, InterestReaction newInterest) {
//        this.notificationType = notificationType;
////        this.user = user;
//        this.newInterest = newInterest;
//    }
//}