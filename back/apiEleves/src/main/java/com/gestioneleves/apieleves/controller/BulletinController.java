package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.BulletinCreateRequest;
import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.dto.BulletinUpdateRequest;
import com.gestioneleves.apieleves.mapper.BulletinMapper;
import com.gestioneleves.apieleves.service.BulletinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Contrôleur REST exposant les opérations CRUD pour la ressource {@code Bulletin}.
 *
 * Points d'entrée:
 * - POST   /api/bulletin           : création d'un bulletin
 * - GET    /api/bulletin           : liste paginée des bulletins
 * - GET    /api/bulletin/{id}      : détail d'un bulletin
 * - PUT    /api/bulletin/{id}      : mise à jour d'un bulletin
 * - DELETE /api/bulletin/{id}      : suppression d'un bulletin
 *
 * Convention de réponses d'erreur:
 * - 404 NOT_FOUND si la ressource demandée n'existe pas (géré par
 *   {@link com.gestioneleves.apieleves.config.ApiExceptionHandler})
 * - 400 BAD_REQUEST en cas d'erreur de validation (@Valid) ou d'arguments invalides
 */
@RestController
@RequestMapping("/api/bulletin")
@RequiredArgsConstructor
public class BulletinController {

    private final BulletinService bulletinService;

    /**
     * Crée un nouveau bulletin.
     *
     * @param request charge utile de création
     * @return 201 Created avec le corps {@link BulletinDTO} et l'en-tête Location vers la ressource créée
     */
    @PostMapping()
    public ResponseEntity<BulletinDTO> createBulletin(@Valid @RequestBody BulletinCreateRequest request) {
        BulletinDTO dto = bulletinService.createBulletin(request);
        return ResponseEntity.created(URI.create("/api/bulletin/" + dto.getIdBulletin())).body(dto);
    }

    /**
     * Récupère la liste paginée des bulletins.
     *
     * @param pageable paramètres de pagination et tri. Par défaut: taille 20, tri par idBulletin
     * @return une page de {@link BulletinDTO}
     */
    @GetMapping()
    public Page<BulletinDTO> getAllBulletins(@PageableDefault(size = 20, sort = "idBulletin") Pageable pageable) {
        return bulletinService.getAllBulletins(pageable).map(BulletinMapper::toDto);
    }

    /**
     * Récupère un bulletin par identifiant.
     *
     * @param id identifiant technique du bulletin
     * @return le bulletin demandé sous forme de {@link BulletinDTO}
     */
    @GetMapping("/{id}")
    public BulletinDTO getBulletinById (@PathVariable Long id){
        return BulletinMapper.toDto(bulletinService.getBulletinById(id));
    }

    /**
     * Met à jour un bulletin existant.
     *
     * @param id identifiant du bulletin
     * @param request payload de mise à jour (champs modifiables uniquement)
     * @return le bulletin mis à jour
     */
    @PutMapping("/{id}")
    public BulletinDTO editBulletin(@PathVariable Long id, @Valid @RequestBody BulletinUpdateRequest request) {
        return bulletinService.editBulletin(id, request);
    }

    /**
     * Supprime un bulletin.
     *
     * @param id identifiant du bulletin à supprimer
     * @return 204 No Content si la suppression a réussi
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
        return ResponseEntity.noContent().build();
    }
}
