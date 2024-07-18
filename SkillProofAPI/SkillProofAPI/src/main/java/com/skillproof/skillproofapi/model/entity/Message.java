//package com.skillproof.skillproofapi.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
//@Table(name = "message")
//public class Message {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "content", nullable = false)
//    @NonNull
//    private String content;
//
//    @Column(name = "timestamp", nullable = false)
//    @NonNull
//    private Timestamp timestamp;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnoreProperties({"messages"})
//    private Chat chat;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JsonIgnoreProperties({"messages", "chats", "jobApplied", "jobsCreated", "comments", "posts",
////            "usersFollowing", "userFollowedBy", "posts", "comments", "notifications", "interestReactions",
////            "jobsCreated", "interactions", "jobApplied", "messages", "chats"})
////    private User userMadeBy;
//}