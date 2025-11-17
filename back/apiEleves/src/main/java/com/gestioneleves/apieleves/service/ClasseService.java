package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.ClasseCreateRequest;
import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.dto.ClasseUpdateRequest;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.ClasseMapper;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    // Variante contrôleur-friendly
    public ClasseDTO createClasse(ClasseCreateRequest request) {
        Classe toSave = ClasseMapper.fromCreate(request);
        Classe saved = createClasse(toSave);
        return ClasseMapper.toDto(saved);
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

    // Variante contrôleur-friendly
    public ClasseDTO editClasse(Long id, ClasseUpdateRequest request) {
        Classe current = getClasseById(id);
        Classe updated = ClasseMapper.applyUpdate(current, request);
        Classe saved = editClasse(id, updated);
        return ClasseMapper.toDto(saved);
    }

    public Classe editClasse(Long id, Classe classe){
        // Récupération ou exception si non trouvé
        Classe existing = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable : " + id));

        // Mise à jour des champs simples
        if (classe.getNomClasse() != null) {
            existing.setNomClasse(classe.getNomClasse());
        }
        if (classe.getNiveauClasse() != null) {
            existing.setNiveauClasse(classe.getNiveauClasse());
        }
        if (classe.getAnneeScolaire() != null) {
            existing.setAnneeScolaire(classe.getAnneeScolaire());
        }

        // Mise à jour de l'objet lié
        if (classe.getEnseignant().getIdUtilisateur() != null) {
            Utilisateur enseignant = utilisateurRepository.findById(classe.getEnseignant().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable : " + classe.getEnseignant().getIdUtilisateur()));
            existing.setEnseignant(enseignant);
        }

        return classeRepository.save(existing);
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
