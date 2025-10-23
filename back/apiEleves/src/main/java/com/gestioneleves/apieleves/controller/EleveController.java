package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.service.EleveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleve")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    @PostMapping()
    public Eleve createEleve(@RequestBody Eleve eleve) {
        return eleveService.createEleve(eleve);
    }

    @GetMapping()
    public List<Eleve> getAllEleves() {
        return eleveService.getAllEleves();
    }

    @GetMapping("/{id}")
    public Eleve getEleveById (@PathVariable Long id){
        return eleveService.getEleveById(id);
    }

    @PutMapping("/{id}")
    public Eleve editEleve(@PathVariable Long id, @RequestBody Eleve eleve) {
        return eleveService.editEleve(id, eleve);
    }

    @DeleteMapping("/{id}")
    public void deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
    }
}
