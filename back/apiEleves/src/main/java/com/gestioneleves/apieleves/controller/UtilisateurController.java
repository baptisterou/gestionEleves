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

@RestController
@RequestMapping("api/utilisateur")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService service;

    @PostMapping
    public UtilisateurDTO ajouterUtilisateur(@Valid @RequestBody UtilisateurCreateRequest request){
        return service.createUtilisateur(request);
    }

    // Création avec rôle explicite (ADMIN uniquement)
    @PostMapping("/admin")
    public UtilisateurDTO ajouterUtilisateurAdmin(@Valid @RequestBody UtilisateurAdminCreateRequest request) {
        return service.createUtilisateurAsAdmin(request);
    }

    @GetMapping
    public Page<UtilisateurDTO> getAllUtilisateurs(@PageableDefault(size = 20, sort = "idUtilisateur") Pageable pageable){
        return service.getAllUtilisateurs(pageable).map(UtilisateurMapper::toDto);
    }

    @GetMapping("/{id}")
    public UtilisateurDTO getById(@PathVariable Long id){
        return UtilisateurMapper.toDto(service.getUtilisateurById(id));
    }

    @PutMapping("/{id}")
    public UtilisateurDTO modifierUtilisateur (@PathVariable Long id, @Valid @RequestBody UtilisateurUpdateRequest request){
        return service.modifierUtilisateur(id, request);
    }

    // Changement de rôle (ADMIN uniquement)
    @PutMapping("/{id}/role")
    public UtilisateurDTO changerRole(@PathVariable Long id, @Valid @RequestBody UtilisateurRoleUpdateRequest request) {
        return service.updateRole(id, request);
    }

    @DeleteMapping("/{id}")
    public void supprimerUtilisateur(@PathVariable Long id){
        service.supprimerUtilisateur(id);
    }

    // Liste ADMIN avec rôle
    @GetMapping("/admin")
    public Page<com.gestioneleves.apieleves.dto.UtilisateurAdminDTO> getAllUtilisateursAdmin(@PageableDefault(size = 20, sort = "idUtilisateur") Pageable pageable) {
        return service.getAllUtilisateurs(pageable).map(com.gestioneleves.apieleves.mapper.UtilisateurMapper::toAdminDto);
    }

    // Détail ADMIN avec rôle
    @GetMapping("/{id}/admin")
    public com.gestioneleves.apieleves.dto.UtilisateurAdminDTO getByIdAdmin(@PathVariable Long id) {
        return com.gestioneleves.apieleves.mapper.UtilisateurMapper.toAdminDto(service.getUtilisateurById(id));
    }
}
