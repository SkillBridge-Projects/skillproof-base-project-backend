package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillproof.skillproofapi.enumerations.NotificationType;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isShown")
    private Boolean isShown = false;

    @Column(name = "type", nullable = false)
    @NonNull
    private NotificationType type;

    @ManyToOne
    @JsonIgnoreProperties(value = {"usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "connection_request_id")
    @JsonIgnoreProperties(value = {"notification"}, allowSetters = true)
    private Connection connectionRequest;

    @OneToOne
    @JoinColumn(name = "new_comment_id")
    @JsonIgnoreProperties(value = {"notification"}, allowSetters = true)
    private Comment newComment;

    @OneToOne
    @JoinColumn(name = "new_interest_id")
    @JsonIgnoreProperties(value = {"notification"}, allowSetters = true)
    private InterestReaction newInterest;

    public Notification(@NonNull NotificationType type, User user, Connection connectionRequest) {
        this.type = type;
        this.user = user;
        this.connectionRequest = connectionRequest;
    }

    public Notification(@NonNull NotificationType type, User user, Comment newComment) {
        this.type = type;
        this.user = user;
        this.newComment = newComment;
    }

    public Notification(@NonNull NotificationType type, User user, InterestReaction newInterest) {
        this.type = type;
        this.user = user;
        this.newInterest = newInterest;
    }

}
