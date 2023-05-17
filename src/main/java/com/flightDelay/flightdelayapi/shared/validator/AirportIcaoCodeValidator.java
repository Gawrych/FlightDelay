package com.flightDelay.flightdelayapi.shared.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@Length(min = 4, max = 7, message = "{error.message.airportIdentLength}")
@Pattern(regexp="^[A-Z0-9-]*$", message = "{error.message.forbiddenCharsInAirportIdent}")
@Target({ PARAMETER, METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface AirportIcaoCodeValidator {

    String message() default "{error.message.invalidAirportIdent}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}