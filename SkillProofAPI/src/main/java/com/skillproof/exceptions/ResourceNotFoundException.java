package com.skillproof.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends SkillProofException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String object, String field, String id){
        super(Messages.msg(Messages.NOT_FOUND, object, field, id));
    }

    public ResourceNotFoundException(String object, String field, Long id){
        super(Messages.msg(Messages.NOT_FOUND, object, field, id));
    }
}
