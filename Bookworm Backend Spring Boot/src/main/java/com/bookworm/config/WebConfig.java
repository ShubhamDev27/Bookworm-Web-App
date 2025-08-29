// src/main/java/com/yourpackage/config/WebConfig.java
package com.bookworm.config; // Ensure this matches your actual package structure

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // This annotation tells Spring this class provides configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply CORS rules to all API endpoints starting with /api/
                .allowedOrigins("http://localhost:5173") // IMPORTANT: This must match your React app's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                .allowedHeaders("Content-Type", "Authorization") // Allow these request headers
                .allowCredentials(true); // Allow sending cookies/authentication headers (if used)
    }
}