package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.experience.ExperienceResponse;
import com.skillproof.model.request.experience.UpdateExperienceRequest;
import com.skillproof.model.request.experience.CreateExperienceRequest;
import com.skillproof.services.experience.ExperienceService;
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
                            content = @Content(schema = @Schema(implementation = ExperienceResponse.class)))
            }
    )
    public ResponseEntity<ExperienceResponse> createExperience(@RequestBody @Valid CreateExperienceRequest createExperienceRequest) {
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
    public ResponseEntity<?> getExperienceByUserId(@PathVariable String userId) {
        LOG.info("Start of getExperiencesByUserId method.");
        List<ExperienceResponse> experienceResponse = experienceService.getExperienceByUserId(userId);
        if (CollectionUtils.isEmpty(experienceResponse)) {
            LOG.info("End of getExperiencesByUserId method.");
            return noContent();
        }
        LOG.info("End of getExperiencesByUserId method.");
        return ok(experienceResponse);
    }

    @GetMapping(value = "/experiences/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Experience by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = ExperienceResponse.class)))
            }
    )
    public ResponseEntity<?> getExperienceById(@PathVariable Long id) {
        LOG.debug("Start of getExperienceById method.");
        ExperienceResponse ExperienceResponse = experienceService.getExperienceById(id);
        LOG.debug("End of getExperienceById method.");
        return ok(ExperienceResponse);
    }

    @GetMapping(value = "/experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Experiences of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = ExperienceResponse.class)))
            }
    )
    public ResponseEntity<List<ExperienceResponse>> listAllExperiences() {
        List<ExperienceResponse> experienceResponses = experienceService.listAllExperiences();
        return ok(experienceResponses);
    }

    @PatchMapping(value = "/experiences/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update experience of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = ExperienceResponse.class)))
            }
    )
    public ResponseEntity<ExperienceResponse> updateExperience(@PathVariable Long id,
                                                               @RequestBody @Valid UpdateExperienceRequest updateExperienceRequest) {
        ExperienceResponse ExperienceResponse = experienceService.updateExperience(id, updateExperienceRequest);
        return ok(ExperienceResponse);
    }

    @DeleteMapping(value = "/experiences/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete experience of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteExperienceById(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return ok();
    }
}
