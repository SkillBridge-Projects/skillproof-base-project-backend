package com.skillproof.services.skill;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Skill;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.skill.CreateSkillRequest;
import com.skillproof.model.request.skill.SkillResponse;
import com.skillproof.model.request.skill.UpdateSkillRequest;
import com.skillproof.repositories.skill.SkillRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private static final Logger LOG = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillServiceImpl(SkillRepository skillRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SkillResponse createSkill(CreateSkillRequest createSkillRequest) {
        LOG.debug("Start of createSkill method - ExperienceServiceImpl");
        User user = userRepository.getUserById(createSkillRequest.getUserId());
        if (user == null) {
            LOG.error("User with id {} not found.", createSkillRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createSkillRequest.getUserId());
        }
        Skill skill = createSkillEntity(createSkillRequest, user);
        skill = skillRepository.createSkill(skill);
        LOG.debug("Start of createSkill method - SkillServiceImpl");
        return getResponse(skill);
    }

    private Skill createSkillEntity(CreateSkillRequest createSkillRequest, User user) {
        LOG.debug("Start of createSkillEntity method - SkillServiceImpl");
        Skill skill = new Skill();
        skill.setTechnology(createSkillRequest.getTechnology());
        skill.setTools(StringUtils.join(createSkillRequest.getTools(), ","));
        skill.setUser(user);
        skill.setCreatedDate(LocalDateTime.now());
        skill.setUpdatedDate(LocalDateTime.now());
        LOG.debug("End of createSkillEntity method - SkillServiceImpl");
        return skill;
    }

    @Override
    public List<SkillResponse> getSkillsByUserId(String userId) {
        List<Skill> skills = skillRepository.getSkillsByUserId(userId);
        return getResponseList(skills);
    }

    @Override
    public SkillResponse getSkillById(Long id) {
        LOG.debug("Start of getSkillById method - SkillServiceImpl");
        Skill skill = skillRepository.getSkillById(id);
        if (ObjectUtils.isEmpty(skill)) {
            LOG.error("Skill with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.SKILL, ObjectConstants.ID, id);
        }
        return getResponse(skill);
    }

    @Override
    public List<SkillResponse> listAllSkills() {
        LOG.debug("Start of listAllSkills method - SkillServiceImpl");
        List<Skill> skills = skillRepository.listAllSkills();
        return getResponseList(skills);
    }

    @Override
    public SkillResponse updateSkill(Long id, UpdateSkillRequest updateSkillRequest) {
        LOG.debug("Start of updateSkill method - SkillServiceImpl");
        Skill skill = skillRepository.getSkillById(id);
        if (ObjectUtils.isEmpty(skill)) {
            LOG.error("Skill with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.SKILL, ObjectConstants.ID, id);
        }
        prepareSkillEntity(skill, updateSkillRequest);
        Skill updatedSkill = skillRepository.updateSkill(skill);
        LOG.debug("End of updateSkill method - SkillServiceImpl");
        return getResponse(updatedSkill);
    }

    @Override
    public void deleteSkillById(Long id) {
        LOG.debug("Start of deleteSkillById method - SkillServiceImpl");
        skillRepository.deleteSkillById(id);
    }

    private void prepareSkillEntity(Skill skill, UpdateSkillRequest updateSkillRequest) {
        LOG.debug("Start of prepareSkillEntity method - SkillServiceImpl");
        if (StringUtils.isNotEmpty(updateSkillRequest.getTechnology())) {
            skill.setTechnology(updateSkillRequest.getTechnology());
        }
        if (CollectionUtils.isNotEmpty(updateSkillRequest.getTools())) {
            skill.setTools(StringUtils.join(updateSkillRequest.getTools(), ","));
        }
        skill.setUpdatedDate(LocalDateTime.now());
        LOG.debug("End of prepareSkillEntity method - SkillServiceImpl");
    }

    private SkillResponse getResponse(Skill skill) {
        SkillResponse skillResponse = ResponseConverter
                .copyProperties(skill, SkillResponse.class);
        skillResponse.setTools(Arrays.asList(skill.getTools().split(",")));
        skillResponse.setUserId(skill.getUser().getId());
        return skillResponse;
    }

    private List<SkillResponse> getResponseList(List<Skill> skills) {
        return skills.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
