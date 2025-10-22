package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.service.BulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BulletinController {

    private final BulletinService bulletinService;

    @PostMapping("/bulletin/create/{id}")
    public Bulletin createBulletin(@RequestBody Bulletin bulletin) {
        return bulletinService.createBulletin(bulletin);
    }

    @GetMapping("/bulletins/")
    public List<Bulletin> getAllBulletins() {
        return bulletinService.getAllBulletins();
    }

    @PutMapping("/bulletin/edit/{id}")
    public Bulletin editBulletin(@PathVariable Long id, @RequestBody Bulletin bulletin) {
        return bulletinService.editBulletin(bulletin);
    }

    @DeleteMapping("/bulletin/delete/{id}")
    public void deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
    }
}