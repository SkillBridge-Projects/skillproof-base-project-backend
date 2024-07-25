package com.skillproof.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {NotEmptyOnlyValidator.class}
)
public @interface NotEmptyOnly {

    String message() default "{skillproof.notNull.notEmpty}";

    Class<?>[] groups() default {};

    String label();

    Class<? extends Payload>[] payload() default {};
}
