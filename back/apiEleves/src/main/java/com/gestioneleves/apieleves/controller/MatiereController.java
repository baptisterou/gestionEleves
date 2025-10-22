package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    @PostMapping("/matiere/create/{id}")
    public Matiere createMatiere(@RequestBody Matiere matiere) {
        return matiereService.createMatiere(matiere);
    }

    @GetMapping("/matieres/")
    public List<Matiere> getAllMatieres() {
        return matiereService.getAllMatieres();
    }

    @PutMapping("/matiere/edit/{id}")
    public Matiere editMatiere(@PathVariable Long id, @RequestBody Matiere matiere) {
        return matiereService.editMatiere(matiere);
    }

    @DeleteMapping("/matiere/delete/{id}")
    public void deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
    }
}