package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.skill.CreateSkillRequest;
import com.skillproof.model.request.skill.SkillResponse;
import com.skillproof.model.request.skill.UpdateSkillRequest;
import com.skillproof.services.skill.SkillService;
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
@Tag(name = "Skill", description = "Stores Skills of users in skillProof App")
public class SkillController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SkillController.class);

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping(value = "/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add Skill of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = SkillResponse.class)))
            }
    )
    public ResponseEntity<SkillResponse> createSkill(@RequestBody @Valid CreateSkillRequest createSkillRequest) {
        LOG.debug("Start of createSkill method.");
        SkillResponse skillResponse = skillService.createSkill(createSkillRequest);
        LOG.debug("End of createSkill method.");
        return created(skillResponse);
    }

    @GetMapping(value = "/users/{userId}/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Skills of a user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = SkillResponse.class)))
            }
    )
    public ResponseEntity<?> getSkillsByUserId(@PathVariable String userId) {
        LOG.debug("Start of getSkillByUserId method.");
        List<SkillResponse> skillResponse = skillService.getSkillsByUserId(userId);
        if (CollectionUtils.isEmpty(skillResponse)) {
            LOG.info("End of getSkillByUserId method.");
            return noContent();
        }
        LOG.debug("End of getSkillByUserId method.");
        return ok(skillResponse);
    }

    @GetMapping(value = "/skills/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Skill by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = SkillResponse.class)))
            }
    )
    public ResponseEntity<?> getSkillById(@PathVariable Long id) {
        LOG.debug("Start of getSkillById method.");
        SkillResponse skillResponse = skillService.getSkillById(id);
        LOG.debug("End of getSkillById method.");
        return ok(skillResponse);
    }

    @GetMapping(value = "/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Skills of a user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = SkillResponse.class)))
            }
    )
    public ResponseEntity<List<SkillResponse>> listAllSkills() {
        List<SkillResponse> skillResponses = skillService.listAllSkills();
        return ok(skillResponses);
    }

    @PatchMapping(value = "/skills/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update skill details of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = SkillResponse.class)))
            }
    )
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id,
                                                     @RequestBody @Valid UpdateSkillRequest updateSkillRequest) {
        SkillResponse skillResponse = skillService.updateSkill(id, updateSkillRequest);
        return ok(skillResponse);
    }

    @DeleteMapping(value = "/skills/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete skill of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteSkillById(@PathVariable Long id) {
        skillService.deleteSkillById(id);
        return ok();
    }
}
