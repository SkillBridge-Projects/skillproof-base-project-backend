package com.skillproof.skillproofapi.exceptions;

import com.skillproof.skillproofapi.exceptions.Messages;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceFoundException extends SkillProofException {

    public ResourceFoundException(String object, String field, String id) {
        super(Messages.msg(Messages.ALREADY_EXISTS, object, field, id));
    }

    public ResourceFoundException(String msg){
        super(msg);
    }

    public ResourceFoundException(String msg, Object... msgArgs){
        super(Messages.msg(msg, msgArgs));
    }
}
