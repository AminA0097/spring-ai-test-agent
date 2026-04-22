package com.webapp.arvand.coreback.Validation;

import com.webapp.arvand.coreback.Annotaions.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Password must contain:
        // - At least 8 characters
        // - At least one digit
        // - At least one lowercase letter
        // - At least one uppercase letter
        // - At least one special character
        // - No whitespace

        boolean hasMinLength = password.length() >= 8;
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasSpecial = password.matches(".*[@#$%^&+=!].*");
        boolean noSpace = !password.contains(" ");

        return hasMinLength && hasDigit && hasLower && hasUpper && hasSpecial && noSpace;
    }
}