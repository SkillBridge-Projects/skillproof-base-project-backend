package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.request.user.UserResponse;
import com.skillproof.skillproofapi.security.SecurityConstants;
import com.skillproof.skillproofapi.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "AccessToken", description = "Generates Token for the user")
public class AccessTokenController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(AccessTokenController.class);

    private final UserService userService;

    public AccessTokenController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/accessToken", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generates Token for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> getAccessToken(@RequestParam String userName){
        LOG.info("Start of getAccessToken method.");
        UserResponse user = userService.getUserByUsername(userName);
        String token = generateToken(user.getUserName());
        LOG.info("End of getAccessToken method.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
