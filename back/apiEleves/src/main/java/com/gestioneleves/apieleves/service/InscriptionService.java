package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.entity.*;
import com.gestioneleves.apieleves.mapper.InscriptionMapper;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.InscriptionRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    public List<Inscription> getAllInscriptions(){ return inscriptionRepository.findAll(); }

    // Variante contrôleur-friendly
    public InscriptionDTO createInscription(InscriptionCreateRequest request){
        Inscription toSave = InscriptionMapper.fromCreate(request);
        Inscription saved = createInscription(toSave);
        return InscriptionMapper.toDto(saved);
    }

    public Inscription createInscription(Inscription inscription){ return inscriptionRepository.save(inscription); }

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    public InscriptionDTO editInscription(Long id, InscriptionDTO request) {
        Inscription current = getInscriptionById(id);
        Inscription updatedEntity = InscriptionMapper.applyUpdate(current, request);
        Inscription saved = editInscription(id, updatedEntity);
        return InscriptionMapper.toDto(saved);
    }

    public Inscription editInscription(Long id, Inscription inscription){
        // Récupération ou exception si non trouvé
        Inscription existing = inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable : " + id));

        // Mise à jour des champs simples
        if (inscription.getDateInscrip() != null) {
            existing.setDateInscrip(inscription.getDateInscrip());
        }

        // Mise à jour de l'élève lié (si fourni)
        if (inscription.getEleve() != null && inscription.getEleve().getIdEleve() != null) {
            Eleve eleve = eleveRepository.findById(inscription.getEleve().getIdEleve())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Élève introuvable : " + inscription.getEleve().getIdEleve()));
            existing.setEleve(eleve);
        }

        // Mise à jour de l'utilisateur lié (si fourni)
        if (inscription.getUtilisateur() != null && inscription.getUtilisateur().getIdUtilisateur() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(inscription.getUtilisateur().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Utilisateur introuvable : " + inscription.getUtilisateur().getIdUtilisateur()));
            existing.setUtilisateur(utilisateur);
        }

        //Sauvegarde et retour
        return inscriptionRepository.save(existing); }

    public void deleteInscription(Long id){
        if (!inscriptionRepository.existsById(id)) {
            throw new EntityNotFoundException("Inscription introuvable:" + id);
        }
        inscriptionRepository.deleteById(id);
    }

    public Inscription getInscriptionById(Long id){
        return inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable: " + id));
    }

    // Récupérer toutes les inscriptions d’un élève
    public List<Inscription> getInscriptionsByEleve(Eleve eleve) {
        return inscriptionRepository.findByEleve(eleve);
    }

    // Récupérer toutes les inscriptions faites par un administrateur
    public List<Inscription> getInscriptionsByAdmin(Utilisateur admin) {
        return inscriptionRepository.findByUtilisateur(admin);
    }
}
