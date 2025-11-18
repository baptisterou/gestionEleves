package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurAdminDTO;
import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.UtilisateurMapper;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour les informations liées à l'utilisateur courant et la liste paginée des utilisateurs.
 *
 * Endpoints:
 * - GET /users/me      : retourne l'utilisateur authentifié (projection admin)
 * - GET /users         : retourne une page d'utilisateurs (projection publique)
 *
 * Sécurité:
 * - L'accès est protégé par Spring Security/JWT (voir {@link com.gestioneleves.apieleves.security.ApplicationSecurityConfig}).
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UtilisateurRepository utilisateurRepository;

    /**
     * Retourne les informations de l'utilisateur authentifié.
     *
     * @param currentUser principal injecté par Spring Security
     * @return projection admin de l'utilisateur courant
     */
    @GetMapping("/me")
    public UtilisateurAdminDTO me(@AuthenticationPrincipal Utilisateur currentUser) {
        return UtilisateurMapper.toAdminDto(currentUser);
    }

    /**
     * Liste paginée des utilisateurs.
     *
     * @param pageable paramètres de pagination/tri (par défaut: 20, tri par idUtilisateur)
     * @return page de {@link UtilisateurDTO}
     */
    @GetMapping
    public Page<UtilisateurDTO> users(@PageableDefault(size = 20, sort = "idUtilisateur") Pageable pageable) {
        return utilisateurRepository.findAll(pageable).map(UtilisateurMapper::toDto);
    }
}
