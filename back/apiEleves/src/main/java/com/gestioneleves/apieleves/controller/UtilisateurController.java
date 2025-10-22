package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurDto;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    final UtilisateurService service;

    @PostMapping("/add")
    public Utilisateur ajouterUtilisateur(@RequestBody Utilisateur utilisateur){
        return service.ajouterUtilisateur(utilisateur);
    };

    @GetMapping()
    public List<Utilisateur> getAllUtilisateurs(){
        return service.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public UtilisateurDto getById(@PathVariable Long id){
        return service.getUtilisateurById(id);
    }

    @PutMapping("/{id}")
    public Utilisateur modifier(@PathVariable Long id, @RequestBody  UtilisateurDto dto){
        return service.modifierUtilisateur(id, dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

}
