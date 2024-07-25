package com.skillproof.exceptions.apiError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timeStamp;

    private String message;
    private List<SubError> subErrors;

    @JsonIgnore
    private String debugMessage;

    private ApiError() {
        timeStamp = LocalDateTime.now();
    }

    public ApiError(List<SubError> subErrors){
        super();
        this.subErrors = subErrors;
    }

    public ApiError(HttpStatus httpStatus, String message, List<SubError> subErrors){
        this();
        this.status = httpStatus;
        this.message = message.trim();
        this.subErrors = new ArrayList<>(subErrors);
    }

    public ApiError(HttpStatus httpStatus, String message, Throwable ex){
        this();
        this.status = httpStatus;
        this.message = message.trim();
        this.debugMessage = ex.getLocalizedMessage();
    }


}
