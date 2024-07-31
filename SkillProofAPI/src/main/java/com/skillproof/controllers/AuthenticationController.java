package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.authentication.JwtResponse;
import com.skillproof.model.request.authentication.UserCredentials;
import com.skillproof.model.request.user.UserResponse;
import com.skillproof.utils.JwtUtil;
import com.skillproof.services.UserDetailsServiceImpl;
import com.skillproof.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "AccessToken", description = "Generates Token for the user")
public class AuthenticationController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService,
                                    AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(value = "/accessToken/{emailAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generates Token for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = JwtResponse.class)))
            }
    )
    public ResponseEntity<JwtResponse> getAccessToken(@PathVariable String emailAddress) {
        LOG.debug("Start of getAccessToken method.");
        UserResponse user = userService.getUserByEmailAddress(emailAddress);
        String token = jwtUtil.createToken(user);
        LOG.debug("End of getAccessToken method.");
        return ok(new JwtResponse(token, user.getId()));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticates User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = JwtResponse.class)))
            }
    )
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody UserCredentials authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserResponse userDetails = userService.getUserByEmailAddress(authenticationRequest.getUserName());
        final String token = jwtUtil.createToken(userDetails);
        return ok(new JwtResponse(token, userDetails.getId()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Username/Password", e);
        }
    }
}
