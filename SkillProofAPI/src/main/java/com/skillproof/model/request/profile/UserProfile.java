package com.skillproof.model.request.profile;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.model.request.experience.ExperienceResponse;
import com.skillproof.model.request.user.UserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class UserProfile {

    private UserResponse user;  // This field will have a reference to UserResponse

    private List<EducationResponse> educationDetails;

    private List<ExperienceResponse> experiences;
}
