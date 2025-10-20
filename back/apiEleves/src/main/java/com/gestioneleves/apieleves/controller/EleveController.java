package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Eleve;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestioneleves.apieleves.service.EleveService;

import java.util.List;

@RestController
public class EleveController {

    private final EleveService eleveService;

    public EleveController(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    @GetMapping("/eleves")
    public List<Eleve> getEleves() {
        return eleveService.getAllEleves();
    }

}
