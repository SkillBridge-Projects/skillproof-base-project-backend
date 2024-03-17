package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    @NonNull
    @Email
    private String userName;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "first_name", nullable = false)
    @NonNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NonNull
    private String lastName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // ------------ DATA MEMBERS WITH RELATIONS ------------------ //

//    @OneToOne(cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("user")
//    private Picture profilePicture;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    /* ----------- SKILLS/EDUCATION/EXPERIENCE ----------- */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<SkillsAndExperience> skillsAndExperiences;

    /* ----------- NETWORK ----------- */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<Connection> users;

    /* ----------- FEED ----------- */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties("owner")
    private Collection<Post> posts;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"recommendedTo", "interestReactions", "comments", "owner"}, allowSetters = true)
    @Fetch(value = FetchMode.SELECT)
    private Collection<Post> recommendedPosts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userMadeBy")
    @JsonIgnoreProperties("userMadeBy")
    private Collection<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Collection<Notification> notifications;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("usersInterested")
    @Fetch(value = FetchMode.SELECT)
    private Collection<InterestReaction> interestReactions;

    /* ----------- JOBS ----------- */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userMadeBy")
    @JsonIgnoreProperties(value = {"usersApplied", "userMadeBy", "recommendedTo"})
    private Collection<Job> jobsCreated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"usersApplied", "userMadeBy", "recommendedTo"})
    private Collection<Job> jobApplied;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"usersApplied", "userMadeBy", "recommendedTo"}, allowSetters = true)
    private Collection<Job> recommendedJobs;

    /* ----------- CHAT ----------- */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userMadeBy")
    @JsonIgnoreProperties("userMadeBy")
    private Collection<Message> messages;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @JsonIgnoreProperties("users")
    private Collection<Chat> chats;

    public User(@NonNull @Email String userName, @NotBlank String password,
                @NonNull String firstName, @NonNull String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
