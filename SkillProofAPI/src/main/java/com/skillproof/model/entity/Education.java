package com.skillproof.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Size(max = 250)
    @NotNull
    @Column(name = "university", nullable = false)
    private String university;

    @Basic
    @Size(max = 250)
    @NotNull
    @Column(name = "college", nullable = false)
    private String college;

    @Basic
    @Size(max = 250)
    @NotNull
    @Column(name = "degree", nullable = false)
    private String degree;

    @Basic
    @Size(max = 250)
    @NotNull
    @Column(name = "grade", nullable = false)
    private Float grade;

    @Basic
    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @Basic
    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @Basic
    @Size(max = 250)
    @NotNull
    @Column(name = "description")
    private String description;

    @Basic
    @NotNull
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

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