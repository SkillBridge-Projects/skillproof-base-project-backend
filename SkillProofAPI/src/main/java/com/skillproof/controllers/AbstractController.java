package com.skillproof.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    ResponseEntity forbidden(){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    private ResponseEntity<String> resp(HttpStatus httpStatus){
        return new ResponseEntity<>(httpStatus);
    }

    ResponseEntity<String> ok(){
        return resp(HttpStatus.OK);
    }
}
