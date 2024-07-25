package com.skillproof.skillproofapi.exceptions;

import com.skillproof.skillproofapi.constants.ObjectConstants;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String field, String id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.USER, field, id));
    }

    public UserNotFoundException(String field, Long id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.USER, field, id));
    }
}
