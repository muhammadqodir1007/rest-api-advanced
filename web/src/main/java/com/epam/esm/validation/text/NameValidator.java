package com.epam.esm.validation.text;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name.length() < 2 || name.length() > 50) return false;
        return name.matches("^[a-zA-Z]+$");
    }
}
