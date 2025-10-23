package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matiere")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    @PostMapping()
    public Matiere createMatiere(@RequestBody Matiere matiere) {
        return matiereService.createMatiere(matiere);
    }

    @GetMapping()
    public List<Matiere> getAllMatieres() {
        return matiereService.getAllMatieres();
    }

    @GetMapping("/{id}")
    public Matiere getMatiereById (@PathVariable Long id){
        return matiereService.getMatiereById(id);
    }

    @PutMapping("/{id}")
    public Matiere editMatiere(@PathVariable Long id, @RequestBody Matiere matiere) {
        return matiereService.editMatiere(id, matiere);
    }

    @DeleteMapping("/{id}")
    public void deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
    }
}
