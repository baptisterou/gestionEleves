package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.mapper.ClasseMapper;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import com.gestioneleves.apieleves.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classe")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;
    private final ClasseRepository classeRepository;

    @PostMapping()
    public ClasseDTO createClasse(@RequestBody ClasseDTO classe) {
        Classe entity = ClasseMapper.fromCreate(classe);
        Classe result = classeService.createClasse(entity);
        return ClasseMapper.toDto(result);
    }

    @GetMapping()
    public List<ClasseDTO> getAllClasses() {
        List<Classe> classes = classeRepository.findAll();
        return ClasseMapper.toDtoList(classes);
    }

    @GetMapping("/{id}")
    public ClasseDTO getClasseById (@PathVariable Long id){
        Classe classe = classeService.getClasseById(id);
        return ClasseMapper.toDto(classe);
    }

    @PutMapping("/{id}")
    public ClasseDTO editClasse(@PathVariable Long id, @RequestBody ClasseDTO classe) {
        Classe entity = ClasseMapper.fromUpdate(classe);
        Classe result = classeService.editClasse(id, entity);
        return ClasseMapper.toDto(result);
    }

    @DeleteMapping("/{id}")
    public void deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
    }
}
