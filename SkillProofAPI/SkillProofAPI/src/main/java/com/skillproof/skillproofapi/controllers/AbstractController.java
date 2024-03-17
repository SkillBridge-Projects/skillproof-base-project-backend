package com.skillproof.skillproofapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.skillproof.skillproofapi.security.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public abstract class AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

    HttpHeaders createToken(String userName){
        LOG.info("Start of createToken method.");
        HttpHeaders responseHeaders = new HttpHeaders();
        String token = generateToken(userName);
        responseHeaders.set(SecurityConstants.HEADER_STRING, token);
        responseHeaders.set("Content-Type", "application/json");
        LOG.info("End of createToken method.");
        return responseHeaders;
    }

    String generateToken(String userName) {
        LOG.info("Start of generateToken method.");
        String token = JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        LOG.info("End of generateToken method.");
        return SecurityConstants.TOKEN_PREFIX + token;
    }

    <T> ResponseEntity<T> created(T body){
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    <T> ResponseEntity<T> ok(T body){
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    ResponseEntity noContent(){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private ResponseEntity<String> resp(HttpStatus httpStatus){
        return new ResponseEntity<>(httpStatus);
    }

    ResponseEntity<String> ok(){
        return resp(HttpStatus.OK);
    }
}
