package com.pedrycz.tobebought.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(
                new LengthRule(8,30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(s));
        return result.isValid();
    }
}
