package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.mapper.InscriptionMapper;
import com.gestioneleves.apieleves.service.InscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des {@code Inscriptions}.
 *
 * Endpoints exposés:
 * - GET    /api/inscription           : liste de toutes les inscriptions
 * - POST   /api/inscription           : création d'une nouvelle inscription
 * - PUT    /api/inscription/{id}      : mise à jour d'une inscription existante
 * - DELETE /api/inscription/{id}      : suppression d'une inscription
 *
 * Erreurs typiques:
 * - 404 NOT_FOUND si l'entité ciblée n'existe pas
 * - 400 BAD_REQUEST en cas d'erreurs de validation (@Valid)
 */
@RestController
@RequestMapping("/api/inscription")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    /**
     * Récupère l'ensemble des inscriptions.
     *
     * @return la liste des {@link InscriptionDTO}
     */
    @GetMapping
    public List<InscriptionDTO> getAllInscriptions() {
        return inscriptionService.getAllInscriptions().stream().map(InscriptionMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Crée une inscription à partir des informations fournies.
     *
     * @param request charge utile de création de l'inscription
     * @return 201 Created avec l'inscription créée et l'en-tête Location
     */
    @PostMapping
    public ResponseEntity<InscriptionDTO> createInscription(@Valid @RequestBody InscriptionCreateRequest request) {
        InscriptionDTO dto = inscriptionService.createInscription(request);
        return ResponseEntity.created(URI.create("/api/inscription/" + dto.getIdInscription())).body(dto);
    }

    /**
     * Met à jour une inscription existante.
     *
     * Remarque: cette variante accepte un {@link InscriptionDTO} en entrée pour simplifier
     * l'appel côté client et applique une mise à jour partielle des champs.
     *
     * @param id identifiant de l'inscription à modifier
     * @param request données modifiées de l'inscription
     * @return l'inscription mise à jour
     */
    @PutMapping("/{id}")
    public InscriptionDTO editInscription(@PathVariable Long id, @Valid @RequestBody InscriptionDTO request) {
        return inscriptionService.editInscription(id, request);
    }

    /**
     * Supprime une inscription.
     *
     * @param id identifiant de l'inscription à supprimer
     * @return 204 No Content si la suppression a réussi
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        inscriptionService.deleteInscription(id);
        return ResponseEntity.noContent().build();
    }
}
