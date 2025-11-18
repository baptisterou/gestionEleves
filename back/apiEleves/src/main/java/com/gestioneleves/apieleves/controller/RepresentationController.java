package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.RepresentationCreateRequest;
import com.gestioneleves.apieleves.dto.RepresentationDTO;
import com.gestioneleves.apieleves.mapper.RepresentationMapper;
import com.gestioneleves.apieleves.service.RepresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des {@code Representation} (représentation d'élèves/parents selon le modèle).
 *
 * Endpoints exposés:
 * - GET    /api/representation          : liste de toutes les représentations
 * - POST   /api/representation          : création d'une représentation
 * - PUT    /api/representation/{id}     : mise à jour d'une représentation
 * - DELETE /api/representation/{id}     : suppression d'une représentation
 *
 * Gestion d'erreurs: voir {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}
 */
@RestController
@RequestMapping("/api/representation")
@RequiredArgsConstructor
public class RepresentationController {

    private final RepresentationService representationService;

    /**
     * Récupère l'ensemble des représentations (non paginé).
     *
     * @return liste des {@link RepresentationDTO}
     */
    @GetMapping
    public List<RepresentationDTO> getAllRepresentations() {
        return representationService.getAllRepresentations().stream().map(RepresentationMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Crée une nouvelle représentation.
     *
     * @param request charge utile de création (validée)
     * @return 201 Created avec la ressource créée et l'en-tête Location
     */
    @PostMapping
    public ResponseEntity<RepresentationDTO> createRepresentation(@Valid @RequestBody RepresentationCreateRequest request) {
        RepresentationDTO dto = representationService.createRepresentation(request);
        return ResponseEntity.created(URI.create("/api/representation/" + dto.getIdRepresentation())).body(dto);
    }

    /**
     * Met à jour une représentation existante.
     *
     * @param id identifiant de la représentation
     * @param request données modifiées
     * @return la représentation mise à jour
     */
    @PutMapping("/{id}")
    public RepresentationDTO editRepresentation(@PathVariable Long id, @Valid @RequestBody RepresentationDTO request) {
        return representationService.editRepresentation(id, request);
    }

    /**
     * Supprime une représentation.
     *
     * @param id identifiant de la ressource à supprimer
     * @return 204 No Content si la suppression réussit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepresentation(@PathVariable Long id) {
        representationService.deleteRepresentation(id);
        return ResponseEntity.noContent().build();
    }
}
