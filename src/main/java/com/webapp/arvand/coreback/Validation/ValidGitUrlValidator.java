package com.webapp.arvand.coreback.Validation;

import com.webapp.arvand.coreback.Annotaions.ValidGitUrl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidGitUrlValidator implements ConstraintValidator<ValidGitUrl, String> {

    private static final Pattern GIT_URL_PATTERN =
            Pattern.compile("^(https?://)?(github\\.com|gitlab\\.com|bitbucket\\.org)/.+$");

    @Override
    public void initialize(ValidGitUrl constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null) {
            return false;
        }
        return GIT_URL_PATTERN.matcher(url).matches();
    }
}