package com.skillproof.skillproofapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.dto.NewUserInfo;
import com.skillproof.skillproofapi.enumerations.RoleType;
import com.skillproof.skillproofapi.model.entity.Role;
import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.user.CreateUserRequest;
import com.skillproof.skillproofapi.repositories.RoleRepository;
import com.skillproof.skillproofapi.repositories.UserDao;
import com.skillproof.skillproofapi.security.SecurityConstants;
import com.skillproof.skillproofapi.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
@Tag(name = "User", description = "Manages Users in skillProof App")
public class UserController {

    private final UserDao userDao;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "SignUp of an User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public ResponseEntity<?> signup(@RequestBody CreateUserRequest createUserRequest) {
        //TODO: Need to refactor this code after creating global response object
        User existingUser = userService.findUserByUsername(createUserRequest.getUserName());
        User user = new User();
        if (existingUser == null) {
            user.setUsername(createUserRequest.getUserName());
            user.setFisrtName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setPhoneNumber(createUserRequest.getPhoneNumber());
            user.setPassword(encoder.encode(createUserRequest.getPassword()));
            Set<Role> roles = new HashSet<>();
            Role r = roleRepository.findByName(RoleType.valueOf(createUserRequest.getRole()));
            roles.add(r);
            user.setRoles(roles);
//                if (file != null) {
//                    Picture pic = new Picture(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
//                    pic.setCompressed(true);
//                    user.setProfilePicture(pic);
//                }
            user = userDao.save(user);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"User with Email already exists!\"}"
                    );
        }

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        responseHeaders.set("Content-Type", "application/json");
        user.setPassword(null);
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Profile",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public User getProfile(@PathVariable Long id) {
        return userService.getProfile(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{id}/user-profile/{otherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Personal Profile of an User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public User getPersonalProfile(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getPersonalProfile(id, otherId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/in/{id}/profile/new-info", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Information of a Profile",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public ResponseEntity informPersonalProfile(@PathVariable Long id, @RequestBody SkillsAndExperience skill) {
        userService.informPersonalProfile(id, skill);
        return ResponseEntity.ok("\"All changes made with success!\"");
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/in/{id}/editJob", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Job of an User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public ResponseEntity editUserJob(@PathVariable Long id, @RequestBody User user) {
        userService.editUserJob(id, user);
        return ResponseEntity.ok("\"All changes made with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{id}/profile/{otherUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Profile of an User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public User showProfile(@PathVariable Long id, @PathVariable Long otherUserId) {
        return userService.showProfile(id, otherUserId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/in/{id}/settings/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change Password for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public ResponseEntity changePassword(@PathVariable Long id, @RequestBody NewUserInfo pwdDetails) {
        //TODO: Need to refactor this code after creating global response object
        if (!pwdDetails.getNewPassword().equals(pwdDetails.getPasswordConfirm())) {
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Passwords do not match!\", "
                            + "\"path\": \"/users/" + id.toString() + "/passwordchange\"}"
                    );
        }
        User user = userService.getUserById(id);
        if (!encoder.matches(pwdDetails.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Wrong password!\", "
                            + "\"path\": \"/users/" + id.toString() + "/passwordchange\"}"
                    );
        }
        user.setPassword(encoder.encode(pwdDetails.getNewPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok("\"Password Changed!\"");
    }


    @CrossOrigin(origins = "*")
    @PutMapping(value = "/in/{id}/settings/change-username", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change UserName for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = User.class)))
            }
    )
    public ResponseEntity changeUserName(@PathVariable Long id, @RequestBody NewUserInfo details) {
        //TODO: Need to refactor this code after creating global response object
        String token = null;
        User existingUser = userService.getUserById(id);

        if (!encoder.matches(details.getCurrentPassword(), existingUser.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Wrong password!\", "
                            + "\"path\": \"/users/" + id.toString() + "/passwordchange\"}"
                    );
        }
        token = JWT.create()
                .withSubject(details.getNewUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        token = SecurityConstants.TOKEN_PREFIX + token;
        existingUser.setUsername(details.getNewUsername());
        userService.saveUser(existingUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.HEADER_STRING, token);
        return ResponseEntity.ok().headers(responseHeaders).body("\"Successful edit!\"");
    }

}
