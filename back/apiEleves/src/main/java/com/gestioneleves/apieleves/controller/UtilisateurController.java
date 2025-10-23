package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
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

    @DeleteMapping("/{id}")
    public UtilisateurService delete (){
        return null;
    }

}
