package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Inscrire;
import com.gestioneleves.apieleves.entity.InscrireId;
import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.repository.InscrireRepository;
import com.gestioneleves.apieleves.service.InscrireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscrireController {

    @Autowired
    private InscrireService inscrireService;

    @GetMapping()
    public List<Inscrire> getAllInscriptions() {
        return inscrireService.getAllInscriptions();
    }

    @PostMapping("/add")
    public Inscrire createInscription(@RequestBody Inscrire inscription) {
        return inscrireService.createInscription(inscription);
    }

    @DeleteMapping("/{id}")
    public void deleteInscription(@PathVariable InscrireId id) {
        inscrireService.deleteInscription(id);
    }
}
