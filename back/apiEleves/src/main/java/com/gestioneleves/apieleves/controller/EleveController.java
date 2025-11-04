package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.service.EleveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleve")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;
    private final EleveRepository eleveRepository;

    @PostMapping()
    public EleveDTO createEleve(@RequestBody EleveDTO eleve) {
        Eleve entity = EleveMapper.fromCreate(eleve);
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
