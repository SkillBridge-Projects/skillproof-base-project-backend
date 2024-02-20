package com.skillproof.skillproofapi.dto;

import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SkillsDTO {
    private Set<SkillsAndExperience> education;
    private Set<SkillsAndExperience> workExperience;
    private Set<SkillsAndExperience> skills;
}
