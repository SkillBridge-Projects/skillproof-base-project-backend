package com.skillproof.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // The post to which the comment belongs

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who created the comment

    // Getters and Setters
}
