package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"chats", "messages"})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat")
    @JsonIgnoreProperties({
            "chat", "jobApplied", "jobsCreated", "comments", "posts", "usersFollowing",
            "userFollowedBy", "posts", "comments", "notifications", "interestReactions", "jobsCreated",
            "interactions", "jobApplied", "messages", "chats"
    })
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Message> messages = new HashSet<>();

}
