package com.webapp.arvand.coreback.Annotaions;
import com.webapp.arvand.coreback.Validation.ValidGitUrlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidGitUrlValidator.class)
public @interface ValidGitUrl {
    String message() default "Not a valid Git repository URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}