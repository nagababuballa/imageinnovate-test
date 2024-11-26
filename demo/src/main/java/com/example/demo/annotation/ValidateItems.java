package com.example.demo.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemsValidation.class)
public @interface ValidateItems {
     String message() default "Items cannot be Empty for an order placed";
}
