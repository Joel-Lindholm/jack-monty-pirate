package com.pirate.jackmonty.validation;

import com.pirate.jackmonty.form.JackMontyForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validation class for Jack Monty Forms
 */
public class JackMontyIslandValidator implements ConstraintValidator<ValidJackMontyIslands, JackMontyForm> {

    /**
     * Validation method for the Jack Monty form
     * Validates that the excluded islands cannot 0 or
     * holds up to the constraint N -2 > X where N is the number of islands for the simulation and X is the excluded islands
     *
     * @param form              the form holding the islands
     * @param constraintContext @{@link ConstraintValidatorContext}
     * @return the validation result
     */
    public boolean isValid(JackMontyForm form, ConstraintValidatorContext constraintContext) {
        return form.getExcludedIslands() > 0 && form.getNbrIslands() - 2 > form.getExcludedIslands();
    }
}