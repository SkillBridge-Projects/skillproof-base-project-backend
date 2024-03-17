package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    @NonNull
    private String content;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"post", "usersFollowing", "userFollowedBy", "posts", "comments",
            "notifications", "interestReactions", "jobsCreated", "interactions", "jobApplied", "messages",
            "chats"}, allowSetters = true)
    private Collection<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    @JsonIgnoreProperties(value = {"post"})
    private Collection<InterestReaction> interestReactions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Collection<Picture> pictures;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "recommendedPosts")
    @JsonIgnoreProperties(value = {"recommendedPosts", "posts", "interestReactions", "posts",
            "usersFollowing", "userFollowedBy", "posts", "comments", "notifications", "interestReactions",
            "jobsCreated", "interactions", "jobApplied", "messages", "chats"}, allowSetters = true)
    private Collection<User> recommendedTo;

    public Post(String content) {
        this.content = content;
    }
}