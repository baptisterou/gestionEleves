package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.EnseignementCreateRequest;
import com.gestioneleves.apieleves.dto.EnseignementDTO;
import com.gestioneleves.apieleves.mapper.EnseignementMapper;
import com.gestioneleves.apieleves.service.EnseignementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enseignement")
@RequiredArgsConstructor
public class EnseignementController {

    private final EnseignementService enseignementService;

    @GetMapping
    public List<EnseignementDTO> getAllEnseignements() {
        return enseignementService.getAllEnseignements().stream().map(EnseignementMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<EnseignementDTO> createEnseignement(@Valid @RequestBody EnseignementCreateRequest request) {
        EnseignementDTO dto = enseignementService.createEnseignement(request);
        return ResponseEntity.created(URI.create("/api/enseignement/" + dto.getIdEnseignement())).body(dto);
    }

    @PutMapping("/{id}")
    public EnseignementDTO editEnseignement(@PathVariable Long id, @Valid @RequestBody EnseignementDTO request) {
        return enseignementService.editEnseignement(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignement(@PathVariable Long id) {
        enseignementService.deleteEnseignement(id);
        return ResponseEntity.noContent().build();
    }
}
