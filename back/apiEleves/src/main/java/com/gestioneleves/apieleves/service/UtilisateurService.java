package com.gestioneleves.apieleves.service;

//import com.gestioneleves.apieleves.dto.UtilisateurDto;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.UtilisateurMapper;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAllUtilisateurs(){
        return utilisateurRepository.findAll();
    }

    public Utilisateur createUtilisateur (Utilisateur utilisateur){
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur modifierUtilisateur(Long id, Utilisateur utilisateur){
        Optional<Utilisateur> entite = utilisateurRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Utilisateur introuvable: " + id);
        }
        if (utilisateur.getNom() != null) {
            entite.get().setNom(utilisateur.getNom());
        }
        if (utilisateur.getPrenom() != null) {
            entite.get().setPrenom(utilisateur.getPrenom());
        }
        if (utilisateur.getEmail() != null) {
            entite.get().setEmail(utilisateur.getEmail());
        }
        if (utilisateur.getMotDePasse() != null) {
            entite.get().setMotDePasse(utilisateur.getMotDePasse());
        }
        if (utilisateur.getNumTel() != null) {
            entite.get().setNumTel(utilisateur.getNumTel());
        }
        if (utilisateur.getDateNaissance() != null) {
            entite.get().setDateNaissance(utilisateur.getDateNaissance());
        }
        return utilisateurRepository.save(entite.get());
    }

    public void supprimerUtilisateur(Long id){
        if (!utilisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur introuvable: " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur getUtilisateurById(Long id){
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable: " + id));
    }
}
