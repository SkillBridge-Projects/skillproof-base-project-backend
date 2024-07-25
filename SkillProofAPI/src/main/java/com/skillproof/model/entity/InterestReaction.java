//package com.skillproof.skillproofapi.model.entity;
//
//import lombok.*;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Table(name = "interest_reaction")
//public class InterestReaction {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "timestamp")
//    private Timestamp timestamp;
//
////    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
////    @OnDelete(action = OnDeleteAction.CASCADE)
////    private User userMadeBy;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Post post;
//
//    @OneToOne(mappedBy = "newInterest")
//    private Notification notification;
//
//    public InterestReaction(User userMadeBy, Post post) {
////        this.userMadeBy = userMadeBy;
//        this.post = post;
//    }
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//}