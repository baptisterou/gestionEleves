package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.EleveCreateRequest;
import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.dto.EleveUpdateRequest;
import com.gestioneleves.apieleves.service.EleveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/eleve")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    @PostMapping
    public ResponseEntity<EleveDTO> createEleve(@Valid @RequestBody EleveCreateRequest request) {
        EleveDTO dto = eleveService.createEleve(request);
        return ResponseEntity.created(URI.create("/api/eleve/" + dto.getIdEleve())).body(dto);
    }

    @GetMapping
    public Page<EleveDTO> getAllEleves(@PageableDefault(size = 20, sort = "idEleve") Pageable pageable) {
        return eleveService.getAllEleves(pageable).map(com.gestioneleves.apieleves.mapper.EleveMapper::toDto);
    }

    @GetMapping("/{id}")
    public EleveDTO getEleveById (@PathVariable Long id){
        return com.gestioneleves.apieleves.mapper.EleveMapper.toDto(eleveService.getEleveById(id));
    }

    @PutMapping("/{id}")
    public EleveDTO editEleve(@PathVariable Long id, @Valid @RequestBody EleveUpdateRequest request) {
        return eleveService.editEleve(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }
}
