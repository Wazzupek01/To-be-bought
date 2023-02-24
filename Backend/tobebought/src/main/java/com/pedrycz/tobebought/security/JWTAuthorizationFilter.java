package com.pedrycz.tobebought.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }

        String retrievedToken = null;

        for(Cookie c: cookies){
            if(c.getName().equals("jwt-token")) retrievedToken = c.getValue();
        }

        if(retrievedToken == null){
            filterChain.doFilter(request, response);
            return;
        }

        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY)).build().verify(retrievedToken).getSubject();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
