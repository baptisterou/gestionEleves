package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.*;
import com.gestioneleves.apieleves.repository.InscrireRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscrireService {

    private final InscrireRepository inscrireRepository;

    public InscrireService(InscrireRepository inscrireRepository) {
        this.inscrireRepository = inscrireRepository;
    }

    public List<Inscrire> getAllInscriptions(){ return inscrireRepository.findAll(); }

    public Inscrire createInscription(Inscrire inscription){ return inscrireRepository.save(inscription); }

    public Inscrire editInscription(InscrireId id, Inscrire inscription){
        // Récupération ou exception si non trouvé
        Inscrire existing = inscrireRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable : " + id));

        // Mise à jour des champs simples
        if (inscription.getDateInscrip() != null) {
            existing.setDateInscrip(inscription.getDateInscrip());
        }

        //Sauvegarde et retour
        return inscrireRepository.save(inscription); }

    public void deleteInscription(InscrireId id){
        if (!inscrireRepository.existsById(id)) {
            throw new EntityNotFoundException("Inscription introuvable: eleve=" + id.getIdEleve() + ", utilisateur=" + id.getIdUtilisateur());
        }
        inscrireRepository.deleteById(id);
    }

    // Récupérer toutes les inscriptions d’un élève
    public List<Inscrire> getInscriptionsByEleve(Eleve eleve) {
        return inscrireRepository.findByEleve(eleve);
    }

    // Récupérer toutes les inscriptions faites par un administrateur
    public List<Inscrire> getInscriptionsByAdmin(Utilisateur admin) {
        return inscrireRepository.findByUtilisateur(admin);
    }
}
