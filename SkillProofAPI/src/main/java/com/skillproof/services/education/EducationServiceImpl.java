package com.skillproof.services.education;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.EducationNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Education;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.education.CreateEducationRequest;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.model.request.education.UpdateEducationRequest;
import com.skillproof.repositories.education.EducationRepository;
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
public class EducationServiceImpl implements EducationService {

    private static final Logger LOG = LoggerFactory.getLogger(EducationServiceImpl.class);

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    public EducationServiceImpl(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EducationResponse createEducation(CreateEducationRequest createEducationRequest) {
        LOG.debug("Start of createEducation method - EducationServiceImpl");
        User user = userRepository.getUserById(createEducationRequest.getUserId());
        if (user == null) {
            LOG.error("User with id {} not found.", createEducationRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createEducationRequest.getUserId());
        }
        Education education = createEducationEntity(createEducationRequest, user);
        education = educationRepository.createEducation(education);
        LOG.debug("End of createExperience method - EducationServiceImpl");
        return getResponse(education);
    }

    @Override
    public List<EducationResponse> getEducationByUserId(String userId) {
        LOG.debug("Start of getEducationByUserId method - EducationServiceImpl");
        User user = userRepository.getUserById(userId);
        if (user == null) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Education> educationList = educationRepository.getEducationByUserId(userId);
        LOG.debug("End of getEducationByUserId method - EducationServiceImpl");
        return getResponseList(educationList);
    }

    @Override
    public List<EducationResponse> listAllEducationDetails() {
        LOG.debug("Start of listAllEducationDetails method - EducationServiceImpl");
        List<Education> educationResponses = educationRepository.listAllEducationDetails();
        return getResponseList(educationResponses);
    }

    @Override
    public EducationResponse updateEducation(Long id, UpdateEducationRequest updateEducationRequest) {
        LOG.debug("Start of updateEducation method - EducationServiceImpl");
        Education education = educationRepository.getEducationById(id);
        if (ObjectUtils.isEmpty(education)) {
            LOG.error("Education with {} not found", id);
            throw new EducationNotFoundException(ObjectConstants.ID, id);
        }
        prepareEducationEntity(education, updateEducationRequest);
        Education updatedEducation = educationRepository.updateEducation(education);
        LOG.debug("End of updateEducation method - EducationServiceImpl");
        return getResponse(updatedEducation);
    }

    @Override
    public void deleteEducationById(Long id) {
        LOG.debug("Start of deleteEducationById method - EducationServiceImpl");
        Education education = educationRepository.getEducationById(id);
        if (ObjectUtils.isEmpty(education)) {
            LOG.error("Education with {} not found", id);
            throw new EducationNotFoundException(ObjectConstants.ID, id);
        }
        educationRepository.deleteEducationById(id);
    }

    @Override
    public EducationResponse getEducationById(Long id) {
        LOG.debug("Start of getEducationById method - EducationServiceImpl");
        Education education = educationRepository.getEducationById(id);
        if (education == null) {
            LOG.error("Education with {} not found", id);
            throw new EducationNotFoundException(ObjectConstants.ID, id);
        }
        return getResponse(education);
    }

    private Education createEducationEntity(CreateEducationRequest createEducationRequest,
                                            User user) {
        LOG.debug("Start of createEducationEntity method - EducationServiceImpl");
        Education education = new Education();
        education.setUniversity(createEducationRequest.getUniversity());
        education.setCollegeOrSchool(createEducationRequest.getCollegeOrSchool());
        education.setDegree(createEducationRequest.getDegree());
        education.setGrade(createEducationRequest.getGrade());
        education.setDescription(createEducationRequest.getDescription());
        education.setStartDate(createEducationRequest.getStartDate());
        education.setEndDate(createEducationRequest.getEndDate());
        education.setCreatedDate(LocalDateTime.now());
        education.setUpdatedDate(LocalDateTime.now());
        education.setUser(user);
        LOG.debug("End of createEducationEntity method - EducationServiceImpl");
        return education;
    }

    private void prepareEducationEntity(Education education, UpdateEducationRequest updateEducationRequest) {
        LOG.debug("Start of prepareEductionEntity method - EducationServiceImpl");
        if (StringUtils.isNotEmpty(updateEducationRequest.getUniversity())) {
            education.setUniversity(updateEducationRequest.getUniversity());
        }
        if (StringUtils.isNotEmpty(updateEducationRequest.getCollegeOrSchool())) {
            education.setCollegeOrSchool(updateEducationRequest.getCollegeOrSchool());
        }
        if (StringUtils.isNotEmpty(updateEducationRequest.getDegree())) {
            education.setDegree(updateEducationRequest.getDegree());
        }
        if (updateEducationRequest.getGrade() != null) {
            education.setGrade(updateEducationRequest.getGrade());
        }
        if (StringUtils.isNotEmpty(updateEducationRequest.getDescription())) {
            education.setDescription(updateEducationRequest.getDescription());
        }
        if (updateEducationRequest.getStartDate() != null) {
            education.setStartDate(updateEducationRequest.getStartDate());
        }
        if (updateEducationRequest.getEndDate() != null) {
            education.setEndDate(updateEducationRequest.getEndDate());
        }
        education.setUpdatedDate(LocalDateTime.now());
        LOG.debug("End of prepareEductionEntity method - EducationServiceImpl");
    }

    private EducationResponse getResponse(Education education) {
        EducationResponse educationResponse = ResponseConverter
                .copyProperties(education, EducationResponse.class);
        educationResponse.setUserId(education.getUser().getId());
        educationResponse.setUserEmail(education.getUser().getEmailAddress());
        return educationResponse;
    }

    private List<EducationResponse> getResponseList(List<Education> educationList) {
        return educationList.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
