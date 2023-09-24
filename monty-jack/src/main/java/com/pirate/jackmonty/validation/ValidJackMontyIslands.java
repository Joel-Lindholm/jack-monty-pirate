package com.pirate.jackmonty.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Form validation annotation
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JackMontyIslandValidator.class)
public @interface ValidJackMontyIslands {
    String message() default "The excluded islands must be N - 2, where N is the number of total islands";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}