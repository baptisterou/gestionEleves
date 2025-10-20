package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.service.BulletinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestioneleves.apieleves.service.EleveService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des bulletins
 * Expose les endpoints API relatifs aux opérations sur les bulletins
 */
@RestController // Indique que cette classe est un contrôleur REST qui retourne du JSON/XML
public class BulletinController {

    // Injection de dépendance via le constructeur
    // Spring fournit automatiquement une instance de BulletinService
    private final BulletinService bulletinService;

    /**
     * Constructeur pour l'injection de dépendance
     * @param bulletinService Le service des bulletins injecté par Spring
     */
    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    /**
     * Endpoint GET pour récupérer la liste de tous les bulletins
     * @return Liste de tous les bulletins au format JSON
     *
     * URL: GET http://localhost:8080/bulletins
     * Réponse: [{"id_bulletin": 1, "trimestre_bulletin": 1, "annee_bulletin": 2024, ...}, ...]
     */
    @GetMapping("/bulletins")
    public List<Bulletin> getBulletins() {
        // Délégation de la logique au service métier
        return bulletinService.getAllBulletins();
    }
}