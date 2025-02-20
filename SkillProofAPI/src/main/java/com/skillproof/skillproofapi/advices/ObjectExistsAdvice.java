package com.skillproof.skillproofapi.advices;

import com.skillproof.skillproofapi.exceptions.ObjectExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ObjectExistsAdvice  {

    @ResponseBody
    @ExceptionHandler(ObjectExistsException.class)
    @ResponseStatus(HttpStatus.FOUND)
    String existsHandler(ObjectExistsException ex) {
        return ex.getMessage();
    }
}