package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.MatiereCreateRequest;
import com.gestioneleves.apieleves.dto.MatiereDTO;
import com.gestioneleves.apieleves.dto.MatiereUpdateRequest;
import com.gestioneleves.apieleves.mapper.MatiereMapper;
import com.gestioneleves.apieleves.service.MatiereService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/matiere")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    @PostMapping
    public ResponseEntity<MatiereDTO> createMatiere(@Valid @RequestBody MatiereCreateRequest request) {
        MatiereDTO dto = matiereService.createMatiere(request);
        return ResponseEntity.created(URI.create("/api/matiere/" + dto.getIdMatiere())).body(dto);
    }

    @GetMapping
    public Page<MatiereDTO> getAllMatieres(@PageableDefault(size = 20, sort = "idMatiere") Pageable pageable) {
        return matiereService.getAllMatieres(pageable).map(MatiereMapper::toDto);
    }

    @GetMapping("/{id}")
    public MatiereDTO getMatiereById (@PathVariable Long id){
        return MatiereMapper.toDto(matiereService.getMatiereById(id));
    }

    @PutMapping("/{id}")
    public MatiereDTO editMatiere(@PathVariable Long id, @Valid @RequestBody MatiereUpdateRequest request) {
        return matiereService.editMatiere(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.noContent().build();
    }
}
