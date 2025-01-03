package com.skillproof.services.education;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Education;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.education.CreateEducationRequest;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.repositories.education.EducationRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.utils.ResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

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
            LOG.error("User with id {} not found", createEducationRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createEducationRequest.getUserId());
        }
        Education education = createEducationEntity(createEducationRequest, user);
        education = educationRepository.createEducation(education);
        LOG.debug("End of createEducation method - EducationServiceImpl");
        return getResponse(education);
    }

    @Override
    public List<EducationResponse> getEducationByUserId(String userId) {
        LOG.debug("Start of getEducationByUserId method - EducationServiceImpl");
        User user = userRepository.getUserById(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Education> educationList = educationRepository.getEducationByUserId(userId);
        LOG.debug("End of getEducationByUserId method - EducationServiceImpl");
        return List.of();
    }

    @Override
    public EducationResponse getEducationDetailsByUserId(String userId) throws AccessDeniedException {

        LOG.debug("Start of getEducationDetailsByUserId method - EducationServiceImpl");

        // Fetch the user's education details
        User user = userRepository.getUserById(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }

        List<Education> educationList = educationRepository.getEducationByDetailsUserId(userId);
        LOG.debug("End of getEducationDetailsByUserId method - EducationServiceImpl");

        // Assuming you want to return the first education record for simplicity
        if (!educationList.isEmpty()) {
            Education education = educationList.get(0); // Return the first education record
            return getResponse(education);
        }
        return null; // Or throw a custom exception if no education found
    }

    private Education createEducationEntity(CreateEducationRequest createEducationRequest, User user) {
        LOG.debug("Start of createEducationEntity method - EducationServiceImpl");
        Education education = new Education();
        education.setUniversity(createEducationRequest.getUniversity());
        education.setCollege(createEducationRequest.getCollege());
        education.setDegree(createEducationRequest.getDegree());
        education.setGrade(createEducationRequest.getGrade());
        education.setDescription(createEducationRequest.getDescription());
        education.setStartDate(createEducationRequest.getStartDate());
        education.setEndDate(createEducationRequest.getEndDate());
        education.setUser(user);
        LOG.debug("End of createEducationEntity method - EducationServiceImpl");
        return education;
    }

    private EducationResponse getResponse(Education education) {
        EducationResponse educationResponse = ResponseConverter.copyProperties(education, EducationResponse.class);
        educationResponse.setUserId(education.getUser().getId());
        educationResponse.setUserEmail(education.getUser().getEmailAddress());
        return educationResponse;
    }
}
