package com.pedrycz.tobebought.validation;

import com.pedrycz.tobebought.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    @Autowired
    UserRepository userRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (userRepository.findByUsername(s).get() != null)
                return false;
        } catch(NoSuchElementException e){

        }
        Pattern pattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,29}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
