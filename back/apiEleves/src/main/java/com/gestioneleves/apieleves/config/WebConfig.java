package com.gestioneleves.apieleves.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration Web MVC complémentaire.
 *
 * Actuellement, expose une configuration CORS permissive vers les frontends de développement
 * courants (React/Vite/Angular en local), en autorisant les méthodes et en-têtes usuels, ainsi
 * que l'envoi des credentials (cookies/Authorization).
 *
 * Attention: Ajuster cette configuration pour la production (origines autorisées, méthodes, etc.).
 */
@Configuration
public class WebConfig {

    @Bean
    /**
     * Configure globalement les règles CORS de l'API.
     *
     * @return un {@link WebMvcConfigurer} appliquant les règles CORS souhaitées
     */
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost:5173", "http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true);
            }
        };
    }
}
