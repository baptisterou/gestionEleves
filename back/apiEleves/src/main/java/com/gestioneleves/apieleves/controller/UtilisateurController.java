package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurDto;
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
    public UtilisateurDto ajouter (@RequestBody UtilisateurDto dto, @RequestParam String password){
        return service.ajouterUtilisateur(dto, password);
    };

    @GetMapping()
    public List<UtilisateurDto> getAll(){
        return service.getAll();
    }

    @PutMapping("/put/{id}")
    public UtilisateurDto post (){

    };

    @DeleteMapping("/{id}")
    public void delete (){

    }

}
