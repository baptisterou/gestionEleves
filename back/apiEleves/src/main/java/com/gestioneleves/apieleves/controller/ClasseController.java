package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.ClasseCreateRequest;
import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.dto.ClasseUpdateRequest;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.mapper.ClasseMapper;
import com.gestioneleves.apieleves.service.ClasseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/classe")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    @PostMapping()
    public ResponseEntity<ClasseDTO> createClasse(@Valid @RequestBody ClasseCreateRequest request) {
        Classe toSave = ClasseMapper.fromCreate(request);
        Classe saved = classeService.createClasse(toSave);
        ClasseDTO dto = ClasseMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/classe/" + dto.getIdClasse())).body(dto);
    }

    @GetMapping()
    public Page<ClasseDTO> getAllClasses(@PageableDefault(size = 20, sort = "idClasse") Pageable pageable) {
        return classeService.getAllClasses(pageable).map(ClasseMapper::toDto);
    }

    @GetMapping("/{id}")
    public ClasseDTO getClasseById (@PathVariable Long id){
        return ClasseMapper.toDto(classeService.getClasseById(id));
    }

    @PutMapping("/{id}")
    public ClasseDTO editClasse(@PathVariable Long id, @RequestBody ClasseUpdateRequest request) {
        Classe current = classeService.getClasseById(id);
        Classe updated = ClasseMapper.applyUpdate(current, request);
        Classe saved = classeService.editClasse(id, updated);
        return ClasseMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
