package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillproof.skillproofapi.enumerations.SkillType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "skills_experiences")
public class SkillsAndExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @NonNull
    @Column(name = "type")
    private SkillType skillType;

    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"education", "workExperience", "skills"}, allowSetters = true)
    private User user;

}
