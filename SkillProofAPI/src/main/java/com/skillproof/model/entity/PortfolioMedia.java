package com.skillproof.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "portfolio_media")
public class PortfolioMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "media_index", nullable = false)
    private Integer mediaIndex;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    @Basic
    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Basic
    @Column(name = "duration", nullable = false)
    private Float duration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id", nullable = false)
    private Portfolio portfolio;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
