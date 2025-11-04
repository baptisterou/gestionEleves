package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
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

    @Autowired
    private UtilisateurRepository utilisateurRepository;


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
        // Récupération ou exception si non trouvé
        Classe existing = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable : " + id));

        // Mise à jour des champs simples
        if (classe.getNomClasse() != null) {
            classe.setNomClasse(classe.getNomClasse());
        }
        if (classe.getNiveauClasse() != null) {
            classe.setNiveauClasse(classe.getNiveauClasse());
        }
        if (classe.getAnneeScolaire() != null) {
            classe.setAnneeScolaire(classe.getAnneeScolaire());
        }

        // Mise à jour de l'objet lié
        if (classe.getEnseignant() != null && classe.getEnseignant().getIdUtilisateur() != null) {
            Utilisateur enseignant = utilisateurRepository.findById(classe.getEnseignant().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable : " + classe.getEnseignant().getIdUtilisateur()));
            existing.setEnseignant(enseignant);
        }

        // Sauvegarde et retour
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
