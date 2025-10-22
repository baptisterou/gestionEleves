package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Matiere;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gestioneleves.apieleves.service.MatiereService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des matières
 * Expose les endpoints API relatifs aux opérations sur les matières
 */
@RestController // Indique que cette classe est un contrôleur REST qui retourne du JSON/XML
public class MatiereController {

    // Injection de dépendance via le constructeur
    // Spring fournit automatiquement une instance de MatiereService
    private final MatiereService matiereService;

    /**
     * Constructeur pour l'injection de dépendance
     * @param matiereService Le service des matières injecté par Spring
     */
    public MatiereController(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    /**
     * Endpoint GET pour récupérer la liste de toutes les matières
     * @return Liste de toutes les matières au format JSON
     *
     * URL: GET http://localhost:8080/matieres
     * Réponse: [{"id_matiere": 1, "intitule_matiere": "Mathématiques"}, ...]
     */
    @GetMapping("/matieres")
    public List<Matiere> getMatieres() {
        // Délégation de la logique au service métier
        return matiereService.getAllMatieres();
    }
}
