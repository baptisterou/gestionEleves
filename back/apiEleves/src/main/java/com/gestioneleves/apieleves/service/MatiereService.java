package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    public List<Matiere> getAllMatieres() {
        return (List<Matiere>) matiereRepository.findAll();  // ✅ Bon cast
    }
}
