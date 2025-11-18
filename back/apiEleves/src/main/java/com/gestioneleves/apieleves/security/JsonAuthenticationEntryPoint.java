package com.gestioneleves.apieleves.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
/**
 * Entry point Spring Security qui renvoie une réponse JSON standardisée en cas d'absence
 * d'authentification (401 Unauthorized).
 *
 * Objectif: uniformiser le format d'erreur côté clients.
 * Corps typique:
 * {
 *   "error": "UNAUTHORIZED",
 *   "message": "...",
 *   "path": "/url"
 * }
 */
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Ecrit une réponse 401 JSON lorsque l'utilisateur n'est pas authentifié.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Map<String, Object> body = Map.of(
                "error", "UNAUTHORIZED",
                "message", authException != null && authException.getMessage() != null ? authException.getMessage() : "Authentification requise",
                "path", request.getRequestURI()
        );
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
