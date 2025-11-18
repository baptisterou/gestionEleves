package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.MatiereCreateRequest;
import com.gestioneleves.apieleves.dto.MatiereDTO;
import com.gestioneleves.apieleves.dto.MatiereUpdateRequest;
import com.gestioneleves.apieleves.mapper.MatiereMapper;
import com.gestioneleves.apieleves.service.MatiereService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Contrôleur REST exposant les opérations CRUD pour la ressource {@code Matiere}.
 *
 * Points d'entrée:
 * - POST /api/matiere: création d'une matière
 * - GET  /api/matiere: liste paginée des matières
 * - GET  /api/matiere/{id}: détail d'une matière
 * - PUT  /api/matiere/{id}: mise à jour d'une matière
 * - DELETE /api/matiere/{id}: suppression d'une matière
 *
 * Convention de réponses d'erreur:
 * - 404 NOT_FOUND si la ressource demandée n'existe pas (géré par {@link com.gestioneleves.apieleves.config.ApiExceptionHandler})
 * - 400 BAD_REQUEST en cas d'erreur de validation (@Valid) ou d'arguments invalides
 */
@RestController
@RequestMapping("/api/matiere")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    /**
     * Crée une nouvelle matière.
     *
     * @param request charge utile de création contenant les champs requis d'une matière
     * @return 201 Created avec le corps {@link MatiereDTO} et l'en-tête Location pointant vers la nouvelle ressource
     */
    @PostMapping
    public ResponseEntity<MatiereDTO> createMatiere(@Valid @RequestBody MatiereCreateRequest request) {
        MatiereDTO dto = matiereService.createMatiere(request);
        return ResponseEntity.created(URI.create("/api/matiere/" + dto.getIdMatiere())).body(dto);
    }

    /**
     * Récupère la liste paginée des matières.
     *
     * @param pageable paramètres de pagination et tri. Par défaut: taille 20, tri par idMatiere.
     * @return une page de {@link MatiereDTO}
     */
    @GetMapping
    public Page<MatiereDTO> getAllMatieres(@PageableDefault(size = 20, sort = "idMatiere") Pageable pageable) {
        return matiereService.getAllMatieres(pageable).map(MatiereMapper::toDto);
    }

    /**
     * Récupère le détail d'une matière par identifiant.
     *
     * @param id identifiant technique de la matière
     * @return la matière demandée sous forme de {@link MatiereDTO}
     */
    @GetMapping("/{id}")
    public MatiereDTO getMatiereById (@PathVariable Long id){
        return MatiereMapper.toDto(matiereService.getMatiereById(id));
    }

    /**
     * Met à jour une matière existante.
     *
     * @param id identifiant de la matière à modifier
     * @param request payload de mise à jour (champs modifiables uniquement)
     * @return la matière mise à jour sous forme de {@link MatiereDTO}
     */
    @PutMapping("/{id}")
    public MatiereDTO editMatiere(@PathVariable Long id, @Valid @RequestBody MatiereUpdateRequest request) {
        return matiereService.editMatiere(id, request);
    }

    /**
     * Supprime une matière par identifiant.
     *
     * @param id identifiant de la matière à supprimer
     * @return 204 No Content si la suppression s'est déroulée avec succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.noContent().build();
    }
}
