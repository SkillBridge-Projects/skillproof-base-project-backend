package com.skillproof.model.request.profile;


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
public class UserProfile {

    private UserResponse user;

    private List<EducationResponse> educationDetails;

    private List<ExperienceResponse> experiences;
}
