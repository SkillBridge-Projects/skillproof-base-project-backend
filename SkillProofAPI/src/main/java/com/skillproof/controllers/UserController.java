package com.skillproof.controllers;

import com.amazonaws.auth.policy.Resource;
import com.amazonaws.services.s3.model.S3Object;
import com.skillproof.constants.SwaggerConstants;
import com.skillproof.enums.RoleType;
import com.skillproof.enums.sort.UserSortType;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.profile.UserProfile;
import com.skillproof.model.request.user.CreateUserRequest;
import com.skillproof.model.request.user.UpdateUserRequest;
import com.skillproof.model.request.user.UserResponse;
import com.skillproof.security.SecurityConstants;
import com.skillproof.services.AWSS3Service;
import com.skillproof.services.user.UserService;
import com.skillproof.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
@Tag(name = "User", description = "Manages Users in skillProof App")
public class UserController extends AbstractController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AWSS3Service awss3Service;

    @PostMapping(value = "/signup", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "SignUp of an User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public ResponseEntity<?> signup(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserResponse user = userService.createUser(createUserRequest);
        String token = jwtUtil.createToken(user.getEmailAddress());
        return ResponseEntity.ok().headers(getResponseHeaders(token)).body(user);
    }

    private HttpHeaders getResponseHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.HEADER_STRING, token);
        responseHeaders.set(SecurityConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        responseHeaders.add(SecurityConstants.EXPOSE_HEADERS, SecurityConstants.AUTH);
        responseHeaders.add(SecurityConstants.ALLOW_HEADERS, SecurityConstants.ALLOW_HEADERS_VALUE);
        return responseHeaders;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all Users",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    public ResponseEntity<List<UserResponse>> listAllUsers(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String emailAddress,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) RoleType roleType,
//            @RequestParam(required = false) List<String> skills,
//            @Parameter(description = "Creation date of user in 'yyyy-MM-dd'T'HH:mm:ss:SSSS' format")
//            @RequestParam(required = false) LocalDateTime createdDate,
//            @Parameter(description = "Updated date of user in 'yyyy-MM-dd'T'HH:mm:ss:SSSS' format")
//            @RequestParam(required = false) LocalDateTime updatedDate,
            @Parameter(description = "Page number, starts at 1")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Page size, should be at least 1")
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @Parameter(description = "Page size, should be at least 1")
            @RequestParam(required = false) List<UserSortType> sort) {
//        List<UserResponse> userResponses = userService.listAllUsers(id, firstName, lastName, emailAddress, city,
//                phone, roleType, skills, createdDate, updatedDate, page, size, sort);
        List<UserResponse> userResponses = userService.listAllUsers();
        return ok(userResponses);
    }

    @PatchMapping(value = "/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update an User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id,
                                                   @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        UserResponse userResponse = userService.updateUser(id, updateUserRequest);
        return ok(userResponse);
    }

    @PatchMapping(value = "/users/{id}/profile-picture", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update profile picture of a User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = UserProfile.class)))
            })
    public ResponseEntity<UserProfile> updateProfilePicture(@PathVariable String id,
                                                            @RequestParam MultipartFile profilePicture) throws Exception {
        UserProfile userProfile = userService.updateProfilePicture(id, profilePicture);
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        return ok(userResponse);
    }

    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ok();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/users/roles/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get users by role",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    public ResponseEntity<List<UserResponse>> listUsersByRole(@PathVariable RoleType role) {
        List<UserResponse> roleUsers = userService.listUsersByRole(role);
        return ok(roleUsers);
    }

    @GetMapping(value = "/users/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get User Profile by ID",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = UserProfile.class)))
            })
    public ResponseEntity<UserProfile> getUserProfileByUserId(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserResponse user = userService.getUserById(id);
        if (!user.getEmailAddress().equals(currentUsername)) {
            return ResponseEntity.status(403).build();
        }
        UserProfile userProfile = userService.getUserProfileByUserId(id);
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping(value = "/users/{id}/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Download a file from AWS S3",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = Resource.class)))
            }
    )
    public ResponseEntity<?> downloadFileFromS3(@PathVariable String id, @RequestParam("fileName") String fileName) {
        try {
            S3Object s3Object = awss3Service.downloadFile(fileName); // Assuming AWSS3Service.downloadFile() returns S3Object
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(s3Object.getObjectContent());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File download failed: " + e.getMessage());
        }
    }
}
