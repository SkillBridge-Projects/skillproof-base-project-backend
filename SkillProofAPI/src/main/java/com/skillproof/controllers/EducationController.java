package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.education.CreateEducationRequest;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.services.education.EducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@CrossOrigin(origins = {"*"})
@RestController
@AllArgsConstructor
@Tag(name = "Education", description = "Stores Education details of users in SkillProof App")
public class EducationController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(EducationController.class);

    private final EducationService educationService;

    @PostMapping(value = "/education", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add education for a User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = EducationResponse.class)))
            }
    )
    public ResponseEntity<EducationResponse> createEducation(@RequestBody @Valid CreateEducationRequest createEducationRequest) {
        LOG.debug("Start of createEducation method - EducationController");
        EducationResponse educationResponse = educationService.createEducation(createEducationRequest);
        LOG.debug("End of createEducation method - EducationController");
        return created(educationResponse);
    }

    @GetMapping(value = "/education/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get education details by User ID (Accessible only by Admin)",
            responses = {
                    @ApiResponse(description = "Education details retrieved successfully",
                            responseCode = "200"),
                    @ApiResponse(description = "Access Denied", responseCode = "403")
            })
    public ResponseEntity<EducationResponse> getEducationDetailsByUserId(@PathVariable String userId) throws AccessDeniedException {
        LOG.debug("Start of getEducationDetailsByUserId method - EducationController");
        EducationResponse educationResponse = educationService.getEducationDetailsByUserId(userId);
        if (educationResponse == null) {
            return ResponseEntity.notFound().build();
        }
        LOG.debug("End of getEducationDetailsByUserId method - EducationController");
        return ResponseEntity.ok(educationResponse);
    }
}
