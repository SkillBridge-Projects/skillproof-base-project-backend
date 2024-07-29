package com.skillproof.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "content", nullable = false)
    private String content;

    @Basic
    @Column(name = "image_url")
    private String imageUrl; // URL for image

    @Basic
    @Column(name = "video_url")
    private String videoUrl; // URL for video

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Comment> comments;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
