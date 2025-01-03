package com.skillproof.exceptions;

import com.skillproof.constants.ObjectConstants;

public class EducationNotFoundException extends ResourceNotFoundException {
    public EducationNotFoundException(String field, String id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.EDUCATION, field, id));
    }

    public EducationNotFoundException(String field, Long id) {
        super(Messages.msg(Messages.NOT_FOUND, ObjectConstants.EDUCATION, field, id));
    }
}
