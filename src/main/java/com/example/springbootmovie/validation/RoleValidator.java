package com.example.springbootmovie.validation;

import com.example.springbootmovie.model.role.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        if (role == null) return false;  // Role must be present
        return Arrays.asList(Role.values()).contains(role);
    }
}
