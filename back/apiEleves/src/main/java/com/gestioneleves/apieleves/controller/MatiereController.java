package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.service.EleveService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gestioneleves.apieleves.service.MatiereService;

import java.util.List;

@RestController
public class MatiereController {

    private final MatiereService matiereService;

    public MatiereController(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    @GetMapping("/matieres")
    public List<Matiere> getMatieres() {
        return matiereService.getAllMatieres();
    }
}
