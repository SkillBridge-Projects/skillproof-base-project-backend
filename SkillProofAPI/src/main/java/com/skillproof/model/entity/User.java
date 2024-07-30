package com.skillproof.model.entity;

import com.skillproof.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
    @Size(max = 20)
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

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
}
