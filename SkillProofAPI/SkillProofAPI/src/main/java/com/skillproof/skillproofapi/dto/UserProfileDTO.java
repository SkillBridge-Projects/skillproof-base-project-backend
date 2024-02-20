package com.skillproof.skillproofapi.dto;

import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import com.skillproof.skillproofapi.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String name;
    private String surname;
    private String position;
    private String company;
    private Boolean isConnected;
    private Set<User> network;
    private Set<SkillsAndExperience> skills;

}
