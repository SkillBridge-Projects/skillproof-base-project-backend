package com.skillproof.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillproof.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"password", "createdDate"})
@Table(name = "user")
public class User {

    @Id
    @Size(max = 20)
    @Column(name = "id", unique = true)
    private String id;

    @Basic
    @Size(max = 100)
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Basic
    @Column(name = "password", nullable = false)
    @JsonIgnore // Password should be ignored during serialization for security reasons
    private String password;

    @Basic
    @Size(max = 100)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Basic
    @Size(max = 100)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Basic
    @Size(max = 250)
    @Column(name = "city", nullable = false)
    private String city;

    @Basic
    @Column(name = "phone")
    private Long phone;

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Basic
    @Column(name = "bio")
    private String bio;

    @Basic
    @Column(name = "profile_picture_url")
    private String profilePicture;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @JsonIgnore
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "skills", nullable = false)
    private String skills;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
        generateUsername(); // Generate username before saving
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
        generateUsername(); // Update username if necessary
    }

    // Method to generate username based on first name and last name
    private void generateUsername() {
        if (this.firstName != null && this.lastName != null) {
            this.username = this.firstName.toLowerCase() + " " + this.lastName.toLowerCase();
        }
    }

    public void setProfilePicturePath(String profilePicturePath) {
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", phone=" + phone +
                ", role=" + role +
                ", bio='" + bio + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", username='" + username + '\'' +
                '}';
    }
}
