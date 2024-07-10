package com.skillproof.skillproofapi.services.skillAndExperience;

import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.skillsAndExperience.CreateSkillsAndExperienceRequest;
import com.skillproof.skillproofapi.model.request.skillsAndExperience.SkillsAndExperienceResponse;
import com.skillproof.skillproofapi.repositories.skillsAndExperience.SkillsAndExperienceRepository;
import com.skillproof.skillproofapi.repositories.user.UserRepository;
import com.skillproof.skillproofapi.services.user.UserService;
import com.skillproof.skillproofapi.utils.ResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillsAndExperienceServiceImpl implements SkillsAndExperienceService {

    private static final Logger LOG = LoggerFactory.getLogger(SkillsAndExperienceServiceImpl.class);

    private final SkillsAndExperienceRepository skillsAndExperienceRepository;
    private final UserRepository userRepository;

    public SkillsAndExperienceServiceImpl(SkillsAndExperienceRepository skillsAndExperienceRepository, UserService userService, UserRepository userRepository) {
        this.skillsAndExperienceRepository = skillsAndExperienceRepository;
        this.userRepository = userRepository;
    }


    @Override
    public SkillsAndExperienceResponse createSkillsAndExperience(CreateSkillsAndExperienceRequest createSkillsAndExperienceRequest) {
        User user = userRepository.getUserById(createSkillsAndExperienceRequest.getUserId());
        if (user == null){
            throw new UserNotFoundException(ObjectConstants.USER, createSkillsAndExperienceRequest.getUserId());
        }
        SkillsAndExperience skillsAndExperience = createSkillsAndExperienceEntity(createSkillsAndExperienceRequest, user);
        skillsAndExperience = skillsAndExperienceRepository.createSkillsAndExperience(skillsAndExperience);
        return getResponse(skillsAndExperience);
    }

    @Override
    public List<SkillsAndExperienceResponse> getSkillsAndExperiencesByUserId(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null){
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<SkillsAndExperience> skillsAndExperiences = skillsAndExperienceRepository.getSkillsAndExperiencesByUserId(userId);
        return getResponseList(skillsAndExperiences);
    }

    private SkillsAndExperience createSkillsAndExperienceEntity(CreateSkillsAndExperienceRequest createSkillsAndExperienceRequest,
                                                                User user) {
        SkillsAndExperience skillsAndExperience = new SkillsAndExperience();
        skillsAndExperience.setSkillType(createSkillsAndExperienceRequest.getSkillType());
        skillsAndExperience.setDescription(createSkillsAndExperienceRequest.getDescription());
        skillsAndExperience.setIsPublic(createSkillsAndExperienceRequest.getIsPublic());
        skillsAndExperience.setCreatedDate(LocalDateTime.now());
        skillsAndExperience.setUpdatedDate(LocalDateTime.now());
        skillsAndExperience.setUser(user);
        return skillsAndExperience;
    }

    private SkillsAndExperienceResponse getResponse(SkillsAndExperience skillsAndExperience){
        SkillsAndExperienceResponse skillsAndExperienceResponse =  ResponseConverter
                .copyProperties(skillsAndExperience, SkillsAndExperienceResponse.class);
        skillsAndExperienceResponse.setUserId(skillsAndExperience.getUser().getId());
        return skillsAndExperienceResponse;
    }

    private List<SkillsAndExperienceResponse> getResponseList(List<SkillsAndExperience> skillsAndExperiences){
        return skillsAndExperiences.stream()
                .map(entity -> {
                    SkillsAndExperienceResponse skillsAndExperienceResponse = ResponseConverter
                            .copyProperties(entity, SkillsAndExperienceResponse.class);
                    skillsAndExperienceResponse.setUserId(entity.getUser().getId());
                    return skillsAndExperienceResponse;
                })
                .collect(Collectors.toList());
    }
}
