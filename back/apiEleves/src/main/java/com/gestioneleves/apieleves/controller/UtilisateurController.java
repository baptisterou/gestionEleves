package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurAdminCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurRoleUpdateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurUpdateRequest;
import com.gestioneleves.apieleves.mapper.UtilisateurMapper;
import com.gestioneleves.apieleves.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion des {@code Utilisateur}.
 *
 * Endpoints:
 * - POST   /api/utilisateur              : création d'un utilisateur (profil standard)
 * - POST   /api/utilisateur/admin        : création d'un utilisateur avec rôle explicite (ADMIN uniquement)
 * - GET    /api/utilisateur              : liste paginée des utilisateurs (projection publique)
 * - GET    /api/utilisateur/{id}         : détail d'un utilisateur (projection publique)
 * - PUT    /api/utilisateur/{id}         : mise à jour des informations d'un utilisateur
 * - PUT    /api/utilisateur/{id}/role    : changement de rôle (ADMIN uniquement)
 * - DELETE /api/utilisateur/{id}         : suppression d'un utilisateur
 * - GET    /api/utilisateur/admin        : liste paginée (projection admin incluant le rôle)
 * - GET    /api/utilisateur/{id}/admin   : détail (projection admin incluant le rôle)
 *
 * Sécurité: voir {@link com.gestioneleves.apieleves.security.ApplicationSecurityConfig}
 * Gestion d'erreurs: voir {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}
 */
@RestController
@RequestMapping("api/utilisateur")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService service;

    /**
     * Crée un utilisateur standard.
     *
     * @param request charge utile de création (validée)
     * @return l'utilisateur créé (projection publique)
     */
    @PostMapping
    public UtilisateurDTO ajouterUtilisateur(@Valid @RequestBody UtilisateurCreateRequest request){
        return service.createUtilisateur(request);
    }

    // Création avec rôle explicite (ADMIN uniquement)
    /**
     * Crée un utilisateur avec rôle explicite (réservé aux administrateurs).
     *
     * @param request payload de création incluant le rôle
     * @return l'utilisateur créé (projection publique)
     */
    @PostMapping("/admin")
    public UtilisateurDTO ajouterUtilisateurAdmin(@Valid @RequestBody UtilisateurAdminCreateRequest request) {
        return service.createUtilisateurAsAdmin(request);
    }

    /**
     * Liste paginée des utilisateurs (projection publique).
     *
     * @param pageable paramètres de pagination/tri (par défaut: 20, tri par idUtilisateur)
     * @return page de {@link UtilisateurDTO}
     */
    @GetMapping
    public Page<UtilisateurDTO> getAllUtilisateurs(@PageableDefault(size = 20, sort = "idUtilisateur") Pageable pageable){
        return service.getAllUtilisateurs(pageable).map(UtilisateurMapper::toDto);
    }

    /**
     * Détail d'un utilisateur (projection publique).
     *
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur demandé
     */
    @GetMapping("/{id}")
    public UtilisateurDTO getById(@PathVariable Long id){
        return UtilisateurMapper.toDto(service.getUtilisateurById(id));
    }

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id identifiant de l'utilisateur
     * @param request champs modifiables
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/{id}")
    public UtilisateurDTO modifierUtilisateur (@PathVariable Long id, @Valid @RequestBody UtilisateurUpdateRequest request){
        return service.modifierUtilisateur(id, request);
    }

    // Changement de rôle (ADMIN uniquement)
    /**
     * Change le rôle d'un utilisateur (ADMIN uniquement).
     *
     * @param id identifiant de l'utilisateur
     * @param request rôle cible
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/{id}/role")
    public UtilisateurDTO changerRole(@PathVariable Long id, @Valid @RequestBody UtilisateurRoleUpdateRequest request) {
        return service.updateRole(id, request);
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id identifiant de l'utilisateur à supprimer
     */
    @DeleteMapping("/{id}")
    public void supprimerUtilisateur(@PathVariable Long id){
        service.supprimerUtilisateur(id);
    }

    // Liste ADMIN avec rôle
    /**
     * Liste paginée des utilisateurs en projection admin (incluant le rôle).
     *
     * @param pageable paramètres de pagination/tri (par défaut: 20, tri par idUtilisateur)
     * @return page de DTO admin
     */
    @GetMapping("/admin")
    public Page<com.gestioneleves.apieleves.dto.UtilisateurAdminDTO> getAllUtilisateursAdmin(@PageableDefault(size = 20, sort = "idUtilisateur") Pageable pageable) {
        return service.getAllUtilisateurs(pageable).map(com.gestioneleves.apieleves.mapper.UtilisateurMapper::toAdminDto);
    }

    // Détail ADMIN avec rôle
    /**
     * Détail admin d'un utilisateur (incluant le rôle).
     *
     * @param id identifiant
     * @return DTO admin
     */
    @GetMapping("/{id}/admin")
    public com.gestioneleves.apieleves.dto.UtilisateurAdminDTO getByIdAdmin(@PathVariable Long id) {
        return com.gestioneleves.apieleves.mapper.UtilisateurMapper.toAdminDto(service.getUtilisateurById(id));
    }
}
