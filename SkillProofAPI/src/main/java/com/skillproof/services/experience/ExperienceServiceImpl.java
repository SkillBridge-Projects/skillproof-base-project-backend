package com.skillproof.services.experience;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Experience;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.experience.CreateExperienceRequest;
import com.skillproof.model.request.experience.ExperienceResponse;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.repositories.experience.ExperienceRepository;
import com.skillproof.utils.ResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private static final Logger LOG = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ExperienceResponse createExperience(CreateExperienceRequest createExperienceRequest) {
        LOG.debug("Start of createExperience method - ExperienceServiceImpl");
        User user = userRepository.getUserById(createExperienceRequest.getUserId());
        if (user == null){
            LOG.error("User with id {} not found.", createExperienceRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createExperienceRequest.getUserId());
        }
        Experience experience = createExperienceEntity(createExperienceRequest, user);
        experience = experienceRepository.createExperience(experience);
        LOG.debug("End of createExperience method - ExperienceServiceImpl");
        return getResponse(experience);
    }

    @Override
    public List<ExperienceResponse> getExperienceByUserId(String userId) {
        LOG.debug("Start of getExperienceByUserId method - ExperienceServiceImpl");
        User user = userRepository.getUserById(userId);
        if (user == null){
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Experience> experiences = experienceRepository.getExperienceByUserId(userId);
        LOG.debug("End of getExperienceByUserId method - ExperienceServiceImpl");
        return getResponseList(experiences);
    }

    private Experience createExperienceEntity(CreateExperienceRequest createExperienceRequest,
                                              User user) {
        LOG.debug("Start of createExperienceEntity method - ExperienceServiceImpl");
        Experience experience = new Experience();
        experience.setCompanyName(createExperienceRequest.getCompanyName());
        experience.setDescription(createExperienceRequest.getDescription());
        experience.setDesignation(createExperienceRequest.getDesignation());
        experience.setExperience(createExperienceRequest.getExperience());
        experience.setCreatedDate(LocalDateTime.now());
        experience.setUpdatedDate(LocalDateTime.now());
        experience.setUser(user);
        LOG.debug("End of createExperienceEntity method - ExperienceServiceImpl");
        return experience;
    }

    private ExperienceResponse getResponse(Experience experience){
        ExperienceResponse experienceResponse =  ResponseConverter
                .copyProperties(experience, ExperienceResponse.class);
        experienceResponse.setUserId(experience.getUser().getId());
        experienceResponse.setUserEmail(experience.getUser().getEmailAddress());
        return experienceResponse;
    }

    private List<ExperienceResponse> getResponseList(List<Experience> experiences){
        return experiences.stream()
                .map(entity -> {
                    ExperienceResponse experienceResponse = ResponseConverter
                            .copyProperties(entity, ExperienceResponse.class);
                    experienceResponse.setUserId(entity.getUser().getId());
                    experienceResponse.setUserEmail(entity.getUser().getEmailAddress());
                    return experienceResponse;
                })
                .collect(Collectors.toList());
    }
}