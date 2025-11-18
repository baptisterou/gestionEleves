package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.ClasseCreateRequest;
import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.dto.ClasseUpdateRequest;
import com.gestioneleves.apieleves.mapper.ClasseMapper;
import com.gestioneleves.apieleves.service.ClasseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Contrôleur REST exposant les opérations CRUD pour la ressource {@code Classe}.
 *
 * Endpoints:
 * - POST   /api/classe           : création d'une classe
 * - GET    /api/classe           : liste paginée des classes
 * - GET    /api/classe/{id}      : détail d'une classe
 * - PUT    /api/classe/{id}      : mise à jour d'une classe
 * - DELETE /api/classe/{id}      : suppression d'une classe
 *
 * Gestion d'erreurs: voir {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}
 */
@RestController
@RequestMapping("/api/classe")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    /**
     * Crée une nouvelle classe.
     *
     * @param request charge utile de création (validée)
     * @return 201 Created avec la classe créée et l'en-tête Location
     */
    @PostMapping()
    public ResponseEntity<ClasseDTO> createClasse(@Valid @RequestBody ClasseCreateRequest request) {
        ClasseDTO dto = classeService.createClasse(request);
        return ResponseEntity.created(URI.create("/api/classe/" + dto.getIdClasse())).body(dto);
    }

    /**
     * Récupère la liste paginée des classes.
     *
     * @param pageable paramètres de pagination/tri (par défaut: 20, tri par idClasse)
     * @return page de {@link ClasseDTO}
     */
    @GetMapping()
    public Page<ClasseDTO> getAllClasses(@PageableDefault(size = 20, sort = "idClasse") Pageable pageable) {
        return classeService.getAllClasses(pageable).map(ClasseMapper::toDto);
    }

    /**
     * Détail d'une classe par identifiant.
     *
     * @param id identifiant de la classe
     * @return la classe demandée
     */
    @GetMapping("/{id}")
    public ClasseDTO getClasseById (@PathVariable Long id){
        return ClasseMapper.toDto(classeService.getClasseById(id));
    }

    /**
     * Met à jour une classe existante.
     *
     * @param id identifiant de la classe
     * @param request payload de mise à jour (partiel)
     * @return la classe mise à jour
     */
    @PutMapping("/{id}")
    public ClasseDTO editClasse(@PathVariable Long id, @Valid @RequestBody ClasseUpdateRequest request) {
        return classeService.editClasse(id, request);
    }

    /**
     * Supprime une classe.
     *
     * @param id identifiant de la classe à supprimer
     * @return 204 No Content si succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
