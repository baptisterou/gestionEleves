package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.ParcoursCreateRequest;
import com.gestioneleves.apieleves.dto.ParcoursDTO;
import com.gestioneleves.apieleves.mapper.ParcoursMapper;
import com.gestioneleves.apieleves.service.ParcoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des {@code Parcours} (parcours scolaires/éducatifs selon le modèle).
 *
 * Endpoints exposés:
 * - GET    /api/parcours           : liste de tous les parcours
 * - POST   /api/parcours           : création d'un parcours
 * - PUT    /api/parcours/{id}      : mise à jour d'un parcours
 * - DELETE /api/parcours/{id}      : suppression d'un parcours
 *
 * Gestion d'erreurs: voir {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}
 */
@RestController
@RequestMapping("/api/parcours")
@RequiredArgsConstructor
public class ParcoursController {

    private final ParcoursService parcoursService;

    /**
     * Récupère l'ensemble des parcours (non paginé).
     *
     * @return liste des {@link ParcoursDTO}
     */
    @GetMapping
    public List<ParcoursDTO> getAllParcours() {
        return parcoursService.getAllParcours().stream().map(ParcoursMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Crée un nouveau parcours.
     *
     * @param request charge utile de création (validée)
     * @return 201 Created avec le parcours créé et l'en-tête Location
     */
    @PostMapping
    public ResponseEntity<ParcoursDTO> createParcours(@Valid @RequestBody ParcoursCreateRequest request) {
        ParcoursDTO dto = parcoursService.createParcours(request);
        return ResponseEntity.created(URI.create("/api/parcours/" + dto.getIdParcours())).body(dto);
    }

    /**
     * Met à jour un parcours existant.
     *
     * @param id identifiant du parcours
     * @param request données modifiées (partielles)
     * @return le parcours mis à jour
     */
    @PutMapping("/{id}")
    public ParcoursDTO editParcours(@PathVariable Long id, @Valid @RequestBody ParcoursDTO request) {
        return parcoursService.editParcours(id, request);
    }

    /**
     * Supprime un parcours par identifiant.
     *
     * @param id identifiant du parcours à supprimer
     * @return 204 No Content si la suppression réussit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcours(@PathVariable Long id) {
        parcoursService.deleteParcours(id);
        return ResponseEntity.noContent().build();
    }
}
