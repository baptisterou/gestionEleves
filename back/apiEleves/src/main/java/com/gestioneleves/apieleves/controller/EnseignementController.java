package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.EnseignementCreateRequest;
import com.gestioneleves.apieleves.dto.EnseignementDTO;
import com.gestioneleves.apieleves.mapper.EnseignementMapper;
import com.gestioneleves.apieleves.service.EnseignementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des {@code Enseignements} (association matière/enseignant/classe, selon le modèle).
 *
 * Endpoints exposés:
 * - GET    /api/enseignement          : liste de tous les enseignements
 * - POST   /api/enseignement          : création d'un enseignement
 * - PUT    /api/enseignement/{id}     : mise à jour d'un enseignement
 * - DELETE /api/enseignement/{id}     : suppression d'un enseignement
 *
 * Convention d'erreurs gérées globalement par {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}.
 */
@RestController
@RequestMapping("/api/enseignement")
@RequiredArgsConstructor
public class EnseignementController {

    private final EnseignementService enseignementService;

    /**
     * Récupère l'ensemble des enseignements.
     *
     * @return liste des {@link EnseignementDTO}
     */
    @GetMapping
    public List<EnseignementDTO> getAllEnseignements() {
        return enseignementService.getAllEnseignements().stream().map(EnseignementMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Crée un nouvel enseignement.
     *
     * @param request charge utile de création
     * @return 201 Created avec l'enseignement créé et l'en-tête Location
     */
    @PostMapping
    public ResponseEntity<EnseignementDTO> createEnseignement(@Valid @RequestBody EnseignementCreateRequest request) {
        EnseignementDTO dto = enseignementService.createEnseignement(request);
        return ResponseEntity.created(URI.create("/api/enseignement/" + dto.getIdEnseignement())).body(dto);
    }

    /**
     * Met à jour un enseignement existant.
     *
     * @param id identifiant de l'enseignement
     * @param request données modifiées
     * @return l'enseignement mis à jour
     */
    @PutMapping("/{id}")
    public EnseignementDTO editEnseignement(@PathVariable Long id, @Valid @RequestBody EnseignementDTO request) {
        return enseignementService.editEnseignement(id, request);
    }

    /**
     * Supprime un enseignement.
     *
     * @param id identifiant de la ressource à supprimer
     * @return 204 No Content si la suppression réussit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignement(@PathVariable Long id) {
        enseignementService.deleteEnseignement(id);
        return ResponseEntity.noContent().build();
    }
}
