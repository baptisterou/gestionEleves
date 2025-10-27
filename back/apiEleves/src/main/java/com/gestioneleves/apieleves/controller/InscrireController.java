package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Inscrire;
import com.gestioneleves.apieleves.entity.InscrireId;
import com.gestioneleves.apieleves.service.InscrireService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscription")
@RequiredArgsConstructor
public class InscrireController {

    private final InscrireService inscrireService;

    @GetMapping()
    public List<Inscrire> getAllInscriptions() {
        return inscrireService.getAllInscriptions();
    }

    @PostMapping()
    public Inscrire createInscription(@RequestBody Inscrire inscription) {
        return inscrireService.createInscription(inscription);
    }

    @DeleteMapping("/{id}")
    public void deleteInscription(@PathVariable InscrireId id) {
        inscrireService.deleteInscription(id);
    }
}
