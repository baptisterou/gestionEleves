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
        return service.createUtilisateur(utilisateur);
    };

    @GetMapping()
    public List<Utilisateur> getAllUtilisateurs(){
        return service.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id){
        return service.getUtilisateurById(id);
    }

    @PutMapping("/{id}")
    public Utilisateur modifierUtilisateur (@PathVariable Long id, @RequestBody  Utilisateur utilisateur){
        return service.modifierUtilisateur(id, utilisateur);
    }

    @DeleteMapping("/{id}")
    public void supprimerUtilisateur(Long id){
        service.supprimerUtilisateur(id);
    }

}
