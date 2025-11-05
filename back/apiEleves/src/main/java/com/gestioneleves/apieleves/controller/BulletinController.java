package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.BulletinCreateRequest;
import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.dto.BulletinUpdateRequest;
import com.gestioneleves.apieleves.mapper.BulletinMapper;
import com.gestioneleves.apieleves.service.BulletinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/bulletin")
@RequiredArgsConstructor
public class BulletinController {

    private final BulletinService bulletinService;

    @PostMapping()
    public ResponseEntity<BulletinDTO> createBulletin(@Valid @RequestBody BulletinCreateRequest request) {
        BulletinDTO dto = bulletinService.createBulletin(request);
        return ResponseEntity.created(URI.create("/api/bulletin/" + dto.getIdBulletin())).body(dto);
    }

    @GetMapping()
    public Page<BulletinDTO> getAllBulletins(@PageableDefault(size = 20, sort = "idBulletin") Pageable pageable) {
        return bulletinService.getAllBulletins(pageable).map(BulletinMapper::toDto);
    }

    @GetMapping("/{id}")
    public BulletinDTO getBulletinById (@PathVariable Long id){
        return BulletinMapper.toDto(bulletinService.getBulletinById(id));
    }

    @PutMapping("/{id}")
    public BulletinDTO editBulletin(@PathVariable Long id, @Valid @RequestBody BulletinUpdateRequest request) {
        return bulletinService.editBulletin(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
        return ResponseEntity.noContent().build();
    }
}
