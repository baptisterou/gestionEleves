package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.dto.RepresentationCreateRequest;
import com.gestioneleves.apieleves.dto.RepresentationDTO;
import com.gestioneleves.apieleves.mapper.InscriptionMapper;
import com.gestioneleves.apieleves.mapper.RepresentationMapper;
import com.gestioneleves.apieleves.service.InscriptionService;
import com.gestioneleves.apieleves.service.RepresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/representation")
@RequiredArgsConstructor
public class RepresentationController {

    private final RepresentationService representationService;

    @GetMapping
    public List<RepresentationDTO> getAllRepresentations() {
        return representationService.getAllRepresentations().stream().map(RepresentationMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<RepresentationDTO> createRepresentation(@Valid @RequestBody RepresentationCreateRequest request) {
        RepresentationDTO dto = representationService.createRepresentation(request);
        return ResponseEntity.created(URI.create("/api/representation/" + dto.getIdRepresentation())).body(dto);
    }

    @PutMapping("/{id}")
    public RepresentationDTO editRepresentation(@PathVariable Long id, @Valid @RequestBody RepresentationDTO request) {
        return representationService.editRepresentation(id, request);
    }

    // Chemin explicite avec 2 IDs
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepresentation(@PathVariable Long id) {
        representationService.deleteRepresentation(id);
        return ResponseEntity.noContent().build();
    }
}
