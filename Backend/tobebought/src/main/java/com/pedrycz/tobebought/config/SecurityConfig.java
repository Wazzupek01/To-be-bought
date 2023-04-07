package com.pedrycz.tobebought.config;

import com.pedrycz.tobebought.security.AuthenticationFilter;
import com.pedrycz.tobebought.security.CustomAuthenticationManager;
import com.pedrycz.tobebought.security.JWTAuthorizationFilter;
import com.pedrycz.tobebought.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserService userService;

    private static final String[] SWAGGER_PATHS = {"/swagger-ui.html", "/docs", "/swagger-resources/**",
            "/swagger-resources", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**", "/proxy/**"};
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, userService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(SWAGGER_PATHS).permitAll()
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);
        return http.build();
    }
}
