package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.service.BulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulletin")
@RequiredArgsConstructor
public class BulletinController {

    private final BulletinService bulletinService;

    @PostMapping()
    public Bulletin createBulletin(@RequestBody Bulletin bulletin) {
        return bulletinService.createBulletin(bulletin);
    }

    @GetMapping()
    public List<Bulletin> getAllBulletins() {
        return bulletinService.getAllBulletins();
    }

    @GetMapping("/{id}")
    public Bulletin getBulletinById (@PathVariable Long id){
        return bulletinService.getBulletinById(id);
    }

    @PutMapping("/{id}")
    public Bulletin editBulletin(@PathVariable Long id, @RequestBody Bulletin bulletin) {
        return bulletinService.editBulletin(id, bulletin);
    }

    @DeleteMapping("/{id}")
    public void deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
    }
}
