package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.request.experience.CreateExperienceRequest;
import com.skillproof.skillproofapi.model.request.experience.ExperienceResponse;
import com.skillproof.skillproofapi.services.experience.ExperienceService;
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
@Tag(name = "Experience", description = "Manages Experiences of users in skillProof App")
public class ExperienceController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ExperienceController.class);

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping(value = "/experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Experience for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CreateExperienceRequest.class)))
            }
    )
    public ResponseEntity<ExperienceResponse> createExperience(@RequestBody @Valid CreateExperienceRequest createExperienceRequest){
        LOG.info("Start of createExperience method.");
        ExperienceResponse experienceResponse = experienceService.createExperience(createExperienceRequest);
        LOG.info("End of createExperience method.");
        return created(experienceResponse);
    }

    @GetMapping(value = "/users/{userId}/experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Experiences for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = ExperienceResponse.class)))
            }
    )
    public ResponseEntity<?> getExperienceByUserId(@PathVariable String userId){
        LOG.info("Start of getExperiencesByUserId method.");
        List<ExperienceResponse> experienceResponse = experienceService.getExperienceByUserId(userId);
        if (CollectionUtils.isEmpty(experienceResponse)){
            LOG.info("End of getExperiencesByUserId method.");
            return noContent();
        }
        LOG.info("End of getExperiencesByUserId method.");
        return ok(experienceResponse);
    }
}
