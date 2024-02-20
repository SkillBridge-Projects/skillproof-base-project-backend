package com.skillproof.skillproofapi.advices;

import com.skillproof.skillproofapi.exceptions.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class EmailExistsAlreadyAdvice  {

    @ResponseBody
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String emailFoundHandler(EmailAlreadyExistsException ex) {
        return ex.getMessage();
    }
}