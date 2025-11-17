package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.ParcoursCreateRequest;
import com.gestioneleves.apieleves.dto.ParcoursDTO;
import com.gestioneleves.apieleves.mapper.ParcoursMapper;
import com.gestioneleves.apieleves.service.ParcoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parcours")
@RequiredArgsConstructor
public class ParcoursController {

    private final ParcoursService parcoursService;

    @GetMapping
    public List<ParcoursDTO> getAllParcours() {
        return parcoursService.getAllParcours().stream().map(ParcoursMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ParcoursDTO> createParcours(@Valid @RequestBody ParcoursCreateRequest request) {
        ParcoursDTO dto = parcoursService.createParcours(request);
        return ResponseEntity.created(URI.create("/api/parcours/" + dto.getIdParcours())).body(dto);
    }

    @PutMapping("/{id}")
    public ParcoursDTO editParcours(@PathVariable Long id, @Valid @RequestBody ParcoursDTO request) {
        return parcoursService.editParcours(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcours(@PathVariable Long id) {
        parcoursService.deleteParcours(id);
        return ResponseEntity.noContent().build();
    }
}
