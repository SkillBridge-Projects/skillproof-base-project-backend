package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.enums.RoleType;
import com.skillproof.skillproofapi.enums.sort.UserSortType;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.user.CreateUserRequest;
import com.skillproof.skillproofapi.model.request.user.UpdateUserRequest;
import com.skillproof.skillproofapi.model.request.user.UserResponse;
import com.skillproof.skillproofapi.security.SecurityConstants;
import com.skillproof.skillproofapi.services.user.UserService;
import com.skillproof.skillproofapi.utils.JwtUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
@Tag(name = "User", description = "Manages Users in skillProof App")
public class UserController extends AbstractController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.HEADER_STRING, token);
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/in/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Profile",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public User getProfile(@PathVariable Long id) {
//        return userService.getProfile(id);
//    }
//
//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/in/{id}/user-profile/{otherId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Personal Profile of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public User getPersonalProfile(@PathVariable Long id, @PathVariable Long otherId) {
//        return userService.getPersonalProfile(id, otherId);
//    }
//
//    @CrossOrigin(origins = "*")
//    @PutMapping(value = "/in/{id}/profile/new-info", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Update Information of a Profile",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity informPersonalProfile(@PathVariable Long id, @RequestBody SkillsAndExperience skill) {
//        userService.informPersonalProfile(id, skill);
//        return ResponseEntity.ok("\"All changes made with success!\"");
//    }
//
//    @CrossOrigin(origins = "*")
//    @PutMapping(value = "/in/{id}/editJob", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Update Job of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity editUserJob(@PathVariable Long id, @RequestBody User user) {
//        userService.editUserJob(id, user);
//        return ResponseEntity.ok("\"All changes made with success!\"");
//    }
//
//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/in/{id}/profile/{otherUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Profile of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public User showProfile(@PathVariable Long id, @PathVariable Long otherUserId) {
//        return userService.showProfile(id, otherUserId);
//    }
//
//    @CrossOrigin(origins = "*")
//    @PutMapping(value = "/in/{id}/settings/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Change Password for User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity changePassword(@PathVariable Long id, @RequestBody NewUserInfo pwdDetails) {
//        //TODO: Need to refactor this code after creating global response object
//        if (!pwdDetails.getNewPassword().equals(pwdDetails.getPasswordConfirm())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
//                            + "\"status\": 400, "
//                            + "\"error\": \"Bad Request\", "
//                            + "\"message\": \"Passwords do not match!\", "
//                            + "\"path\": \"/users/" + id.toString() + "/passwordchange\"}"
//                    );
//        }
//        User user = userService.getUserById(id);
//        if (!encoder.matches(pwdDetails.getCurrentPassword(), user.getPassword())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
//                            + "\"status\": 400, "
//                            + "\"error\": \"Bad Request\", "
//                            + "\"message\": \"Wrong password!\", "
//                            + "\"path\": \"/users/" + id.toString() + "/passwordchange\"}"
//                    );
//        }
//        user.setPassword(encoder.encode(pwdDetails.getNewPassword()));
//        userService.saveUser(user);
//        return ResponseEntity.ok("\"Password Changed!\"");
//    }


//    @CrossOrigin(origins = "*")
//    @PutMapping(value = "/in/{id}/settings/change-username", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Change UserName for User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity changeUserName(@PathVariable Long id, @RequestBody NewUserInfo details) {
//        //TODO: Need to refactor this code after creating global response object
//        String token = null;
//        User existingUser = userService.getUserById(id);
//
//        if (!encoder.matches(details.getCurrentPassword(), existingUser.getPassword())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
//                            + "\"status\": 400, "
//                            + "\"error\": \"Bad Request\", "
//                            + "\"message\": \"Wrong password!\", "
//                            + "\"path\": \"/users/" + id.toString() + "/passwordchange\"}"
//                    );
//        }
//        token = JWT.create()
//                .withSubject(details.getNewUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
//        token = SecurityConstants.TOKEN_PREFIX + token;
//        existingUser.setEmailAddress(details.getNewUsername());
//        userService.saveUser(existingUser);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set(SecurityConstants.HEADER_STRING, token);
//        return ResponseEntity.ok().headers(responseHeaders).body("\"Successful edit!\"");
//    }

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
            @RequestParam(required = false) RoleType roleType,
            @RequestParam(required = false) List<String> skills,
            @Parameter(description = "Creation date of user in 'yyyy-MM-dd'T'HH:mm:ss:SSSS' format")
            @RequestParam(required = false) LocalDateTime createdDate,
            @Parameter(description = "Updated date of user in 'yyyy-MM-dd'T'HH:mm:ss:SSSS' format")
            @RequestParam(required = false) LocalDateTime updatedDate,
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
}
