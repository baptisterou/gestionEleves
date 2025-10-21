package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.model.Classe;
import com.gestioneleves.apieleves.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @GetMapping("/classes")
    public Iterable<Classe> geAllClasses() {
        return classeService.getAllClasses();
    }
}
