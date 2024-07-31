package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.education.CreateEducationRequest;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.model.request.education.UpdateEducationRequest;
import com.skillproof.services.education.EducationService;
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
@Tag(name = "Education", description = "Stores Education details of users in skillProof App")
public class EducationController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(EducationController.class);

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping(value = "/education", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add Education of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = EducationResponse.class)))
            }
    )
    public ResponseEntity<EducationResponse> createEducation(@RequestBody @Valid CreateEducationRequest createEducationRequest) {
        LOG.debug("Start of createEducation method.");
        EducationResponse educationResponse = educationService.createEducation(createEducationRequest);
        LOG.debug("End of createEducation method.");
        return created(educationResponse);
    }

    @GetMapping(value = "/users/{userId}/education", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Education details of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = EducationResponse.class)))
            }
    )
    public ResponseEntity<?> getEducationByUserId(@PathVariable String userId) {
        LOG.debug("Start of getEducationByUserId method.");
        List<EducationResponse> educationResponse = educationService.getEducationByUserId(userId);
        if (CollectionUtils.isEmpty(educationResponse)) {
            LOG.debug("End of getEducationByUserId method.");
            return noContent();
        }
        LOG.debug("End of getEducationByUserId method.");
        return ok(educationResponse);
    }

    @GetMapping(value = "/education/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Education by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = EducationResponse.class)))
            }
    )
    public ResponseEntity<?> getEducationById(@PathVariable Long id) {
        LOG.debug("Start of getEducationById method.");
        EducationResponse educationResponse = educationService.getEducationById(id);
        LOG.debug("End of getEducationById method.");
        return ok(educationResponse);
    }

    @GetMapping(value = "/education", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Education details of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = EducationResponse.class)))
            }
    )
    public ResponseEntity<List<EducationResponse>> listAllEducationDetails() {
        List<EducationResponse> educationResponses = educationService.listAllEducationDetails();
        return ok(educationResponses);
    }

    @PatchMapping(value = "/education/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update education details of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = EducationResponse.class)))
            }
    )
    public ResponseEntity<EducationResponse> updateEducation(@PathVariable Long id,
                                                             @RequestBody @Valid UpdateEducationRequest updateEducationRequest) {
        EducationResponse educationResponse = educationService.updateEducation(id, updateEducationRequest);
        return ok(educationResponse);
    }

    @DeleteMapping(value = "/education/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete education detail of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteEducationById(@PathVariable Long id) {
        educationService.deleteEducationById(id);
        return ok();
    }
}
