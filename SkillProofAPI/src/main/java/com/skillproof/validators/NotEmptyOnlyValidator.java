package com.skillproof.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyOnlyValidator implements ConstraintValidator<NotEmptyOnly, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if (value == null){
            return true;
        }
        return !value.isEmpty();
    }
}
