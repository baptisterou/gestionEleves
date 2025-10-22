package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.service.EleveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    @PostMapping("/eleve/create/{id}")
    public Eleve createEleve(@RequestBody Eleve eleve) {
        return eleveService.createEleve(eleve);
    }

    @GetMapping("/eleves/")
    public List<Eleve> getAllEleves() {
        return eleveService.getAllEleves();
    }

    @PutMapping("/eleve/edit/{id}")
    public Eleve editEleve(@PathVariable Long id, @RequestBody Eleve eleve) {
        return eleveService.editEleve(eleve);
    }

    @DeleteMapping("/eleve/delete/{id}")
    public void deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
    }
}