package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    @NonNull
    private String content;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"usersFollowing", "userFollowedBy", "posts", "comments",
            "notifications", "interestReactions", "jobsCreated", "interactions", "jobApplied",
            "messages", "chats"})
    private User userMadeBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"usersFollowing", "userFollowedBy", "posts", "comments", "notifications",
            "interestReactions", "jobsCreated", "interactions", "jobApplied", "messages", "chats"})
    private Post post;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "newComment")
    private Notification notification;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}