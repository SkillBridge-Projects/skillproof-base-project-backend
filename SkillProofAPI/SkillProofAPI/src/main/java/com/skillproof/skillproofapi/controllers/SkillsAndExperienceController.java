package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.request.skillsAndExperience.CreateSkillsAndExperienceRequest;
import com.skillproof.skillproofapi.model.request.skillsAndExperience.SkillsAndExperienceResponse;
import com.skillproof.skillproofapi.services.skillAndExperience.SkillsAndExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "SkillsAndExperience", description = "Manages SkillsAndExperiences of users in skillProof API")
public class SkillsAndExperienceController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SkillsAndExperienceController.class);

    private final SkillsAndExperienceService skillsAndExperienceService;

    public SkillsAndExperienceController(SkillsAndExperienceService skillsAndExperienceService) {
        this.skillsAndExperienceService = skillsAndExperienceService;
    }

    @PostMapping(value = "/skills-experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create SkillsAndExperience for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CreateSkillsAndExperienceRequest.class)))
            }
    )
    public ResponseEntity<SkillsAndExperienceResponse> createSkillsAndExperience(@RequestBody @Valid CreateSkillsAndExperienceRequest createSkillsAndExperienceRequest){
        LOG.info("Start of createSkillsAndExperience method.");
        SkillsAndExperienceResponse skillsAndExperienceResponse = skillsAndExperienceService
                .createSkillsAndExperience(createSkillsAndExperienceRequest);
        LOG.info("End of createSkillsAndExperience method.");
        return created(skillsAndExperienceResponse);
    }

    @GetMapping(value = "/users/{userId}/skills-experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get SkillsAndExperiences for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = SkillsAndExperienceResponse.class)))
            }
    )
    public ResponseEntity<?> getSkillsAndExperiencesByUserId(@PathVariable Long userId){
        LOG.info("Start of getSkillsAndExperiencesByUserId method.");
        List<SkillsAndExperienceResponse> skillsAndExperienceResponses = skillsAndExperienceService
                .getSkillsAndExperiencesByUserId(userId);
        if (CollectionUtils.isEmpty(skillsAndExperienceResponses)){
            LOG.info("End of getSkillsAndExperiencesByUserId method.");
            return noContent();
        }
        LOG.info("End of getSkillsAndExperiencesByUserId method.");
        return ok(skillsAndExperienceResponses);
    }
}
