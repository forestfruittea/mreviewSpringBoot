package com.example.springbootmovie.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Invalid role. Allowed values: ROLE_CUSTOMER, ROLE_MODERATOR, ROLE_ADMIN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
