package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public Iterable<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public Optional<Classe> getClasse(Long id) { return classeRepository.findById(id); }

    public Classe addClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public void deleteClasse(Long id) { classeRepository.deleteById(id); }
}
