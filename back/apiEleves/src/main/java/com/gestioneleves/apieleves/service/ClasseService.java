package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public Classe createClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public List<Classe> getAllClasses() {
        return (List<Classe>) classeRepository.findAll();
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

        return classeRepository.save(entite.get());
    }

    public Classe getClasseById(Long id){
        return classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable: " + id));
    }

    public void deleteClasse (Long id_classe) {
        classeRepository.deleteById(id_classe);
    }
}
