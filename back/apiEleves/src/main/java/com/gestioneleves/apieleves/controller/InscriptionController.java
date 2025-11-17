package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.mapper.InscriptionMapper;
import com.gestioneleves.apieleves.service.InscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscription")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @GetMapping
    public List<InscriptionDTO> getAllInscriptions() {
        return inscriptionService.getAllInscriptions().stream().map(InscriptionMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<InscriptionDTO> createInscription(@Valid @RequestBody InscriptionCreateRequest request) {
        InscriptionDTO dto = inscriptionService.createInscription(request);
        return ResponseEntity.created(URI.create("/api/inscription/" + dto.getIdInscription())).body(dto);
    }

    @PutMapping("/{id}")
    public InscriptionDTO editInscription(@PathVariable Long id, @Valid @RequestBody InscriptionDTO request) {
        return inscriptionService.editInscription(id, request);
    }

    // Chemin explicite avec 2 IDs
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        inscriptionService.deleteInscription(id);
        return ResponseEntity.noContent().build();
    }
}
