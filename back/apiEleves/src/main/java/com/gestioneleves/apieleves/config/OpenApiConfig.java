package com.gestioneleves.apieleves.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration OpenAPI/Swagger pour activer le bouton "Authorize" (cadenas)
 * avec un schéma HTTP Bearer (JWT).
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API Gestion Élèves", version = "v1"),
        security = {@SecurityRequirement(name = "bearerAuth")}
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
    // Pas de bean nécessaire: les annotations suffisent pour Springdoc
}
