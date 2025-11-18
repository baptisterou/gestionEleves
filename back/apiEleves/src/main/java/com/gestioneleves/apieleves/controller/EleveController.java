package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.EleveCreateRequest;
import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.dto.EleveUpdateRequest;
import com.gestioneleves.apieleves.service.EleveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Contrôleur REST exposant les opérations CRUD pour la ressource {@code Eleve}.
 *
 * Endpoints:
 * - POST   /api/eleve            : création d'un élève
 * - GET    /api/eleve            : liste paginée des élèves
 * - GET    /api/eleve/{id}       : détail d'un élève
 * - PUT    /api/eleve/{id}       : mise à jour d'un élève
 * - DELETE /api/eleve/{id}       : suppression d'un élève
 *
 * Gestion d'erreurs: voir {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}
 */
@RestController
@RequestMapping("/api/eleve")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    /**
     * Crée un nouvel élève.
     *
     * @param request charge utile de création (validée)
     * @return 201 Created avec l'élève créé et l'en-tête Location
     */
    @PostMapping
    public ResponseEntity<EleveDTO> createEleve(@Valid @RequestBody EleveCreateRequest request) {
        EleveDTO dto = eleveService.createEleve(request);
        return ResponseEntity.created(URI.create("/api/eleve/" + dto.getIdEleve())).body(dto);
    }

    /**
     * Récupère la liste paginée des élèves.
     *
     * @param pageable paramètres de pagination et tri (par défaut: 20, tri par idEleve)
     * @return page de {@link EleveDTO}
     */
    @GetMapping
    public Page<EleveDTO> getAllEleves(@PageableDefault(size = 20, sort = "idEleve") Pageable pageable) {
        return eleveService.getAllEleves(pageable).map(com.gestioneleves.apieleves.mapper.EleveMapper::toDto);
    }

    /**
     * Récupère le détail d'un élève.
     *
     * @param id identifiant de l'élève
     * @return l'élève correspondant
     */
    @GetMapping("/{id}")
    public EleveDTO getEleveById (@PathVariable Long id){
        return com.gestioneleves.apieleves.mapper.EleveMapper.toDto(eleveService.getEleveById(id));
    }

    /**
     * Met à jour un élève existant.
     *
     * @param id identifiant de l'élève
     * @param request champs à mettre à jour (partiel)
     * @return l'élève mis à jour
     */
    @PutMapping("/{id}")
    public EleveDTO editEleve(@PathVariable Long id, @Valid @RequestBody EleveUpdateRequest request) {
        return eleveService.editEleve(id, request);
    }

    /**
     * Supprime un élève.
     *
     * @param id identifiant de l'élève à supprimer
     * @return 204 No Content si succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }
}
