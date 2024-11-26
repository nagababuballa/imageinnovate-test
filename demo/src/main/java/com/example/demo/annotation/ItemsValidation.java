package com.example.demo.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;

import java.util.List;

public class ItemsValidation implements ConstraintValidator<ValidateItems,List> {
    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return !list.isEmpty();
    }
}
