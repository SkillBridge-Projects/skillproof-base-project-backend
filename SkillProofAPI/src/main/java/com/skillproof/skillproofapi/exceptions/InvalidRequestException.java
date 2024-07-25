package com.skillproof.skillproofapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends SkillProofException {

    public InvalidRequestException(String msg){
        super(msg);
    }

    public InvalidRequestException(String msg, Object... msgArgs){
        super(Messages.msg(msg, msgArgs));
    }
}
