package com.skillproof.skillproofapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillproof.skillproofapi.enumerations.SkillType;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class SkillsAndExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "type") @NonNull
    private SkillType type;

    @Column(name = "description") @NonNull
    private String description;

    @Column(name = "isPublic") @NonNull
    private Integer isPublic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"education","workExperience","skills"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userExp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"education","workExperience","skills"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userEdu;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"education","workExperience","skills"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userSk;

}
