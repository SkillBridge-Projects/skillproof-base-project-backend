package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.request.authentication.JwtResponse;
import com.skillproof.skillproofapi.model.request.authentication.UserCredentials;
import com.skillproof.skillproofapi.utils.JwtUtil;
import com.skillproof.skillproofapi.services.UserDetailsServiceImpl;
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
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> getAccessToken(@PathVariable String emailAddress) {
        LOG.info("Start of getAccessToken method.");
        String token = jwtUtil.createToken(emailAddress);
        LOG.info("End of getAccessToken method.");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticates User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = JwtResponse.class)))
            }
    )
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        final String token = jwtUtil.createToken(userDetails.getUsername());
        return ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Username/Password", e);
        }
    }
}
