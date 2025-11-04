package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurUpdateRequest;
import com.gestioneleves.apieleves.entity.Utilisateur;
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
        Utilisateur toSave = UtilisateurMapper.fromCreate(request);
        Utilisateur saved = service.createUtilisateur(toSave);
        return UtilisateurMapper.toDto(saved);
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
    public UtilisateurDTO modifierUtilisateur (@PathVariable Long id, @RequestBody UtilisateurUpdateRequest request){
        Utilisateur part = UtilisateurMapper.fromUpdate(request);
        Utilisateur updated = service.modifierUtilisateur(id, part);
        return UtilisateurMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void supprimerUtilisateur(@PathVariable Long id){
        service.supprimerUtilisateur(id);
    }
}
