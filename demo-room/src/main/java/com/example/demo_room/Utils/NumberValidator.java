package com.example.demo_room.Utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class NumberValidator implements ConstraintValidator<JustNumber,Integer> {

    @Override
    public void initialize(JustNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid( Integer value, ConstraintValidatorContext context) {
        return value != null && value>=0;

    }
}
