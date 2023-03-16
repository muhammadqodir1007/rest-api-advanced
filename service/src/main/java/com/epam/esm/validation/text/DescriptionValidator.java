package com.epam.esm.validation.text;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescriptionValidator implements ConstraintValidator<ValidDescription, String> {


    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {

        if (description.length() < 5 || description.length() > 500) return false;


        return description.matches("^[a-zA-Z]+$");
    }
}
