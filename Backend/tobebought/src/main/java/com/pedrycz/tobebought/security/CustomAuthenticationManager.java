package com.pedrycz.tobebought.security;

import com.pedrycz.tobebought.model.user.UserLoginDTO;
import com.pedrycz.tobebought.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomAuthenticationManager(UserService userService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(authentication.getName());
        try {
            UserLoginDTO user = userService.loginUser(authentication.getName());
            if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
                throw new BadCredentialsException("Incorrect Password");

            return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
        } catch(EntityNotFoundException e) {
            throw new BadCredentialsException("Username doesn't exist");
        }
    }
}
