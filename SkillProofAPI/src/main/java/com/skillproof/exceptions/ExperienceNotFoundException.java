package com.skillproof.exceptions;

import com.skillproof.constants.ObjectConstants;

public class ExperienceNotFoundException extends ResourceNotFoundException {
    public ExperienceNotFoundException(String field, String id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.EXPERIENCE, field, id));
    }

    public ExperienceNotFoundException(String field, Long id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.EXPERIENCE, field, id));
    }
}
