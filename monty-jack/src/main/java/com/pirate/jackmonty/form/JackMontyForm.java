package com.pirate.jackmonty.form;

import com.pirate.jackmonty.validation.ValidJackMontyIslands;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * A simple form object.
 */
@Getter
@Setter
@ValidJackMontyIslands
public class JackMontyForm {

    @Min(3)
    @Max(267570)
    int nbrIslands;

    int excludedIslands;

}
