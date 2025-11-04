package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurGestionDTO;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.UtilisateurMapper;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import com.gestioneleves.apieleves.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/utilisateur")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService service;
    private final UtilisateurRepository utilisateurRepository;

    @PostMapping()
    public UtilisateurDTO ajouterUtilisateur(@RequestBody UtilisateurGestionDTO utilisateur){
        Utilisateur entity = UtilisateurMapper.fromCreate(utilisateur);
        Utilisateur result = service.createUtilisateur(entity);
        return UtilisateurMapper.toDto(result);
    }

    @GetMapping()
    public List<UtilisateurDTO> getAllUtilisateurs(){
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return UtilisateurMapper.toDtoList(utilisateurs);
    }

    @GetMapping("/{id}")
    public UtilisateurDTO getById(@PathVariable Long id){
        Utilisateur utilisateur = service.getUtilisateurById(id);
        return UtilisateurMapper.toDto(utilisateur);
    }

    @PutMapping("/{id}")
    public UtilisateurDTO editUtilisateur (@PathVariable Long id, @RequestBody UtilisateurGestionDTO utilisateur){
        Utilisateur entity = UtilisateurMapper.fromUpdate(utilisateur);
        Utilisateur result = service.editUtilisateur(id, entity);
        return UtilisateurMapper.toDto(result);
    }

    @DeleteMapping("/{id}")
    public void supprimerUtilisateur(@PathVariable Long id){
        service.supprimerUtilisateur(id);
    }
}