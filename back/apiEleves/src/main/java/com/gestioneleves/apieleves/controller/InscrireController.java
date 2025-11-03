package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.entity.Inscrire;
import com.gestioneleves.apieleves.entity.InscrireId;
import com.gestioneleves.apieleves.mapper.InscrireMapper;
import com.gestioneleves.apieleves.service.InscrireService;
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
public class InscrireController {

    private final InscrireService inscrireService;

    @GetMapping
    public List<InscriptionDTO> getAllInscriptions() {
        return inscrireService.getAllInscriptions().stream().map(InscrireMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<InscriptionDTO> createInscription(@Valid @RequestBody InscriptionCreateRequest request) {
        Inscrire toSave = InscrireMapper.fromCreate(request);
        Inscrire saved = inscrireService.createInscription(toSave);
        InscriptionDTO dto = InscrireMapper.toDto(saved);
        URI location = URI.create(String.format("/api/inscription/%d/%d", dto.getEleveId(), dto.getUtilisateurId()));
        return ResponseEntity.created(location).body(dto);
    }

    // Chemin explicite avec 2 IDs
    @DeleteMapping("/{eleveId}/{utilisateurId}")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long eleveId, @PathVariable Long utilisateurId) {
        inscrireService.deleteInscription(new InscrireId(eleveId, utilisateurId));
        return ResponseEntity.noContent().build();
    }
}
