package com.pedrycz.tobebought.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                // Enable cross-origin request handling for the specified path pattern.
                // Exact path mapping URIs (such as "/admin") are supported as well as Ant-style path patterns (such as "/admin/**").
                .addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost", "localhost", "localhost:3000")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowedMethods("POST", "PUT", "GET", "DELETE")
        ;
    }
}
