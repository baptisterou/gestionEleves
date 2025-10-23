package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @PostMapping()
    public Classe createClasse(@RequestBody Classe classe) {
        return classeService.createClasse(classe);
    }

    @GetMapping()
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{id}")
    public Classe getClasseById (@PathVariable Long id){
        return classeService.getClasseById(id);
    }

    @PutMapping("/{id}")
    public Classe editClasse(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.editClasse(id, classe);
    }

    @DeleteMapping("/{id}")
    public void deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
    }
}
