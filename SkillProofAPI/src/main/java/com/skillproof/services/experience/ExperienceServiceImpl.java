package com.skillproof.services.experience;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ExperienceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Experience;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.experience.CreateExperienceRequest;
import com.skillproof.model.request.experience.ExperienceResponse;
import com.skillproof.model.request.experience.UpdateExperienceRequest;
import com.skillproof.repositories.experience.ExperienceRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
        if (user == null) {
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
        if (user == null) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Experience> experiences = experienceRepository.getExperienceByUserId(userId);
        LOG.debug("End of getExperienceByUserId method - ExperienceServiceImpl");
        return getResponseList(experiences);
    }

    @Override
    public ExperienceResponse getExperienceById(Long id) {
        LOG.debug("Start of getExperienceById method - ExperienceServiceImpl");
        Experience experience = experienceRepository.getExperienceById(id);
        if (experience == null) {
            LOG.error("Experience with {} not found", id);
            throw new ExperienceNotFoundException(ObjectConstants.ID, id);
        }
        return getResponse(experience);
    }

    @Override
    public List<ExperienceResponse> listAllExperiences() {
        LOG.debug("Start of listAllExperienceDetails method - ExperienceServiceImpl");
        List<Experience> experienceResponses = experienceRepository.listAllExperienceDetails();
        return getResponseList(experienceResponses);
    }

    @Override
    public ExperienceResponse updateExperience(Long id, UpdateExperienceRequest updateExperienceRequest) {
        LOG.debug("Start of updateExperience method - ExperienceServiceImpl");
        Experience experience = experienceRepository.getExperienceById(id);
        if (ObjectUtils.isEmpty(experience)) {
            LOG.error("Experience with {} not found", id);
            throw new ExperienceNotFoundException(ObjectConstants.ID, id);
        }
        prepareExperienceEntity(experience, updateExperienceRequest);
        Experience updatedExperience = experienceRepository.updateExperience(experience);
        LOG.debug("End of updateExperience method - ExperienceServiceImpl");
        return getResponse(updatedExperience);
    }

    @Override
    public void deleteExperienceById(Long id) {
        LOG.debug("Start of deleteExperienceById method - ExperienceServiceImpl");
        Experience experience = experienceRepository.getExperienceById(id);
        if (ObjectUtils.isEmpty(experience)) {
            LOG.error("Experience with {} not found", id);
            throw new ExperienceNotFoundException(ObjectConstants.ID, id);
        }
        experienceRepository.deleteExperienceById(id);
    }

    private Experience createExperienceEntity(CreateExperienceRequest createExperienceRequest,
                                              User user) {
        LOG.debug("Start of createExperienceEntity method - ExperienceServiceImpl");
        Experience experience = new Experience();
        experience.setCompanyName(createExperienceRequest.getCompanyName());
        experience.setDescription(createExperienceRequest.getDescription());
        experience.setDesignation(createExperienceRequest.getDesignation());
        experience.setStartDate(createExperienceRequest.getStartDate());
        experience.setEndDate(createExperienceRequest.getEndDate());
        experience.setUser(user);
        LOG.debug("End of createExperienceEntity method - ExperienceServiceImpl");
        return experience;
    }

    private void prepareExperienceEntity(Experience experience, UpdateExperienceRequest updateExperienceRequest) {
        LOG.debug("Start of prepareEductionEntity method - experienceServiceImpl");
        if (StringUtils.isNotEmpty(updateExperienceRequest.getCompanyName())) {
            experience.setCompanyName(updateExperienceRequest.getCompanyName());
        }
        if (StringUtils.isNotEmpty(updateExperienceRequest.getDesignation())) {
            experience.setDesignation(updateExperienceRequest.getDesignation());
        }
        if (StringUtils.isNotEmpty(updateExperienceRequest.getDescription())) {
            experience.setDescription(updateExperienceRequest.getDescription());
        }
        if (updateExperienceRequest.getStartDate() != null) {
            experience.setStartDate(updateExperienceRequest.getStartDate());
        }
        if (updateExperienceRequest.getEndDate() != null) {
            experience.setEndDate(updateExperienceRequest.getEndDate());
        }
        LOG.debug("End of prepareEductionEntity method - experienceServiceImpl");
    }

    private ExperienceResponse getResponse(Experience experience) {
        ExperienceResponse experienceResponse = ResponseConverter
                .copyProperties(experience, ExperienceResponse.class);
        experienceResponse.setUserId(experience.getUser().getId());
        experienceResponse.setUserEmail(experience.getUser().getEmailAddress());
        return experienceResponse;
    }

    private List<ExperienceResponse> getResponseList(List<Experience> experiences) {
        return experiences.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
