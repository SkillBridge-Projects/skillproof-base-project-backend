package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.constants.SwaggerConstants;
import com.linkedin.linkedinclone.model.Job;
import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.services.job.JobsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@RestController
@AllArgsConstructor
@Tag(name = "Jobs", description = "Manages Jobs of users in skillProof App")
public class JobsController {

    private final JobsService jobsService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/in/{userId}/new-job", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add New Job for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = Job.class)))
            }
    )
    public ResponseEntity newJob(@PathVariable Long userId, @RequestBody Job job) {
        jobsService.newJob(userId, job);
        return ResponseEntity.ok("\"Job created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{userId}/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List Jobs for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = Job.class)))
            }
    )
    public Set<Job> getJobs(@PathVariable Long userId) {
        return jobsService.getJobs(userId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/in/{userId}/jobs/make-application/{jobId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add New Application in Job for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = Job.class)))
            }
    )
    public ResponseEntity newApplication(@PathVariable Long userId, @PathVariable Long jobId) {
        boolean isCreated = jobsService.newApplication(userId, jobId);
        if (!isCreated){
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Application has already been made!\", "
                    );
        }
        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{userId}/jobs/{jobId}/applicants", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List Job Applicants for Job for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = Job.class)))
            }
    )
    public Set<User> getJobApplicants(@PathVariable Long userId, @PathVariable Long jobId) {
        return jobsService.getJobApplicants(userId, jobId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{userId}/recommended-jobs", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Recommended Jobs for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = Job.class)))
            }
    )
    public List<Job> getRecommendedJobs(@PathVariable Long userId) {
        return jobsService.getRecommendedJobs(userId);
    }
}
