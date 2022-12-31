package com.pedrycz.tobebought.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{7,29}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
