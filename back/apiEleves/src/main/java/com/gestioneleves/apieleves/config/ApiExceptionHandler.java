package com.gestioneleves.apieleves.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gestion centralisée des exceptions de l'API.
 *
 * Cette classe convertit les exceptions courantes en réponses JSON normalisées avec
 * des codes HTTP appropriés. Les contrôleurs restent ainsi focalisés sur la logique métier.
 *
 * Format de réponse typique:
 * {
 *   "error": "CODE_SYMBOLIQUE",
 *   "message": "Message lisible",
 *   (optionnel) "fields": { "champ": "erreur de validation" }
 * }
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Convertit une {@link EntityNotFoundException} en réponse 404.
     *
     * @param ex exception remontée par la couche service/répository
     * @return corps JSON standardisé
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(EntityNotFoundException ex) {
        return Map.of(
                "error", "NOT_FOUND",
                "message", ex.getMessage()
        );
    }

    /**
     * Convertit une {@link IllegalArgumentException} en réponse 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(IllegalArgumentException ex) {
        return Map.of(
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        );
    }

    /**
     * Gère les erreurs de validation (@Valid) et renvoie 400 avec le détail des champs en erreur.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage, (a, b) -> a));
        return Map.of(
                "error", "VALIDATION_ERROR",
                "fields", fieldErrors
        );
    }

    /**
     * Convertit une {@link AccessDeniedException} en réponse 403.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleForbidden(AccessDeniedException ex) {
        return Map.of(
                "error", "FORBIDDEN",
                "message", ex.getMessage() != null ? ex.getMessage() : "Accès refusé"
        );
    }
}
