package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.mapper.BulletinMapper;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import com.gestioneleves.apieleves.service.BulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulletin")
@RequiredArgsConstructor
public class BulletinController {

    private final BulletinService bulletinService;
    private final BulletinRepository bulletinRepository;

    @PostMapping()
    public BulletinDTO createBulletin(@RequestBody BulletinDTO bulletin) {
        Bulletin entity = BulletinMapper.fromCreate(bulletin);
        Bulletin result = bulletinService.createBulletin(entity);
        return BulletinMapper.toDto(result);
    }

    @GetMapping()
    public List<BulletinDTO> getAllBulletins() {
        List<Bulletin> bulletins = bulletinRepository.findAll();
        return BulletinMapper.toDtoList(bulletins);
    }

    @GetMapping("/{id}")
    public BulletinDTO getBulletinById (@PathVariable Long id){
        Bulletin bulletin = bulletinService.getBulletinById(id);
        return BulletinMapper.toDto(bulletin);
    }

    @PutMapping("/{id}")
    public BulletinDTO editBulletin(@PathVariable Long id, @RequestBody BulletinDTO bulletin) {
        Bulletin entity = BulletinMapper.fromUpdate(bulletin);
        Bulletin result = bulletinService.editBulletin(id, entity);
        return BulletinMapper.toDto(result);
    }

    @DeleteMapping("/{id}")
    public void deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
    }
}
