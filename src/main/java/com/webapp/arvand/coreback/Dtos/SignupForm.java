package com.webapp.arvand.coreback.Dtos;

import com.webapp.arvand.coreback.Annotaions.UniqueEmail;
import com.webapp.arvand.coreback.Annotaions.ValidGitUrl;
import com.webapp.arvand.coreback.Annotaions.ValidPassword;
import com.webapp.arvand.coreback.Validation.AdvancedValidation;
import com.webapp.arvand.coreback.Validation.BasicValidation;
import com.webapp.arvand.coreback.Annotaions.UniqueUsername;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@GroupSequence({SignupForm.class, AdvancedValidation.class})
public class SignupForm {

    @NotBlank(message = "Username is required", groups = BasicValidation.class)
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters",
            groups = BasicValidation.class)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
            message = "Username can only contain letters, numbers, dots, underscores and hyphens",
            groups = BasicValidation.class)
    @UniqueUsername(message = "Username already exists", groups = AdvancedValidation.class)
    private String userName;

    @NotBlank(message = "Password is required", groups = BasicValidation.class)
    @ValidPassword(message = "Password is too weak", groups = AdvancedValidation.class)
    private String passWord;

    @NotBlank(message = "Email is required", groups = BasicValidation.class)
    @Email(message = "Invalid email format", groups = BasicValidation.class)
    @UniqueEmail(message = "Email already registered", groups = AdvancedValidation.class)
    private String email;

    @NotBlank(message = "Repository URL is required", groups = BasicValidation.class)
    @URL(message = "Invalid URL format", groups = BasicValidation.class)
    @ValidGitUrl(message = "Not a valid Git repository URL", groups = AdvancedValidation.class)
    private String repoUrl;
}
