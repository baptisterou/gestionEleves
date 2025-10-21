package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service pour la gestion des élèves
 * Contient la logique métier relative aux opérations sur les élèves
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class EleveService {

    // Injection du repository pour accéder aux données des élèves
    // Spring fournit automatiquement une instance de EleveRepository
    @Autowired
    private EleveRepository eleveRepository;

    /**
     * Récupère la liste de tous les élèves
     *
     * @return Liste des objets Eleve contenant tous les élèves en base de données
     */
    public Eleve createEleve(String nom_eleve, String prenom_eleve, Date naissance_eleve) {
        return eleveRepository.save(
                new Eleve(nom_eleve, prenom_eleve, naissance_eleve)
        );
    }

    public List<Eleve> getAllEleves() {
        // Appel au repository pour récupérer tous les élèves
        // Le cast en List<Eleve> est nécessaire car findAll() retourne un Iterable
        return (List<Eleve>) eleveRepository.findAll();
    }

    public Eleve editEleve(Long id_eleve, String nom_eleve, String prenom_eleve, Date naissance_eleve) {
        Eleve eleve = eleveRepository.findById(id_eleve).orElseThrow();
        eleve.setNom_eleve(nom_eleve);
        eleve.setPrenom_eleve(prenom_eleve);
        eleve.setNaissance_eleve(naissance_eleve);

        return eleveRepository.save(eleve);
    }

    public void deleteEleve(Long id_eleve) {
        eleveRepository.deleteById(id_eleve);
    }
}