package com.skillproof.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Size(max = 100)
    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Basic
    @Size(max = 100)
    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @Basic
    @Size(max = 250)
    @Column(name = "description")
    private String description;

    @Basic
    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @Basic
    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

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
