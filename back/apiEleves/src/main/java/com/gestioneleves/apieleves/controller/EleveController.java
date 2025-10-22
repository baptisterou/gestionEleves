package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Eleve;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestioneleves.apieleves.service.EleveService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des élèves
 * Expose les endpoints API relatifs aux opérations sur les élèves
 */
@RestController // Indique que cette classe est un contrôleur REST qui retourne du JSON/XML
public class EleveController {

    // Injection de dépendance via le constructeur
    // Spring fournit automatiquement une instance de EleveService
    private final EleveService eleveService;

    /**
     * Constructeur pour l'injection de dépendance
     * @param eleveService Le service des élèves injecté par Spring
     */
    public EleveController(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    /**
     * Endpoint GET pour récupérer la liste de tous les élèves
     * @return Liste de tous les élèves au format JSON
     *
     * URL: GET http://localhost:8080/eleves
     * Réponse: [{"id_eleve": 1, "nom_eleve": "Dupont", "prenom_eleve": "Jean", ...}, ...]
     */
    @GetMapping("/eleves")
    public List<Eleve> getEleves() {
        // Délégation de la logique au service métier
        return eleveService.getAllEleves();
    }
}
