package com.skillproof.skillproofapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.user.UserResponse;
import com.skillproof.skillproofapi.security.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public abstract class AbstractController {

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
