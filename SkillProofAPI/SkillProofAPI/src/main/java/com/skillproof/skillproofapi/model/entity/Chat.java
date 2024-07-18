//package com.skillproof.skillproofapi.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.Collection;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "chat")
//public class Chat {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "timestamp")
//    private Timestamp timestamp;
//
////    @ManyToMany(fetch = FetchType.LAZY)
////    @JsonIgnoreProperties({"chats", "messages"})
////    private Collection<User> users;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
//    @JsonIgnoreProperties({
//            "chat", "jobApplied", "jobsCreated", "comments", "posts", "usersFollowing",
//            "userFollowedBy", "posts", "comments", "notifications", "interestReactions", "jobsCreated",
//            "interactions", "jobApplied", "messages", "chats"
//    })
//    private Collection<Message> messages;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//}