package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;

    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public Classe createClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public Page<Classe> getAllClasses(Pageable pageable) {
        return classeRepository.findAll(pageable);
    }

    public Classe editClasse(Long id, Classe classe){
        Optional<Classe> entite = classeRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Classe introuvable: " + id);
        }
        if (classe.getNomClasse() != null) {
            entite.get().setNomClasse(classe.getNomClasse());
        }
        if (classe.getNiveauClasse() != null) {
            entite.get().setNiveauClasse(classe.getNiveauClasse());
        }
        if (classe.getAnneeScolaire() != null) {
            entite.get().setAnneeScolaire(classe.getAnneeScolaire());
        }
        if (classe.getEnseignant() != null) {
            entite.get().setEnseignant(classe.getEnseignant());
        }
        return classeRepository.save(entite.get());
    }

    public Classe getClasseById(Long id){
        return classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable: " + id));
    }

    public void deleteClasse (Long id_classe) {
        if (!classeRepository.existsById(id_classe)) {
            throw new EntityNotFoundException("Classe introuvable: " + id_classe);
        }
        classeRepository.deleteById(id_classe);
    }
}
