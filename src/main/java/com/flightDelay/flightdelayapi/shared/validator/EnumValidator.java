package com.flightDelay.flightdelayapi.shared.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = EnumValidatorConstraint.class)
@NotNull
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();

    String message() default "error.message.incorrectEnumType";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}