package com.gestioneleves.apieleves.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
/**
 * Handler Spring Security qui renvoie une réponse JSON standardisée en cas d'accès interdit (403).
 *
 * Objectif: fournir un format d'erreur homogène pour les clients front-end.
 * Corps typique:
 * {
 *   "error": "FORBIDDEN",
 *   "message": "...",
 *   "path": "/url"
 * }
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Ecrit une réponse 403 JSON lorsque l'utilisateur est authentifié mais non autorisé.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Map<String, Object> body = Map.of(
                "error", "FORBIDDEN",
                "message", accessDeniedException != null && accessDeniedException.getMessage() != null ? accessDeniedException.getMessage() : "Accès refusé",
                "path", request.getRequestURI()
        );
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
