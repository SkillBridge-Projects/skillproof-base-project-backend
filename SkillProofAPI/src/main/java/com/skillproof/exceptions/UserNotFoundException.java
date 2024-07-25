package com.skillproof.exceptions;

import com.skillproof.constants.ObjectConstants;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String field, String id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.USER, field, id));
    }

    public UserNotFoundException(String field, Long id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.USER, field, id));
    }
}
