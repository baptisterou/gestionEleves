package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulletinService {

    @Autowired
    private BulletinRepository bulletinRepository;

    public List<Bulletin> getAllBulletins() {
        return (List<Bulletin>) bulletinRepository.findAll();  // ✅ Bon cast
    }
}
