package com.pedrycz.tobebought.validation;

import com.pedrycz.tobebought.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pedrycz.tobebought.services.UserServiceImpl.unwrapUser;

public class UsernameValidator implements ConstraintValidator<Username, String> {


    private final UserRepository userRepository;

    @Autowired
    public UsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            unwrapUser(userRepository.findByUsername(s));
        } catch(EntityNotFoundException e){
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,29}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
