package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Role;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilisateur> getAllUtilisateurs(){
        return utilisateurRepository.findAll();
    }

    public Page<Utilisateur> getAllUtilisateurs(Pageable pageable){
        return utilisateurRepository.findAll(pageable);
    }

    @Transactional
    public Utilisateur createUtilisateur (Utilisateur utilisateur){
        // unicité email
        utilisateurRepository.findByEmail(utilisateur.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email déjà utilisé");
        });
        // encoder le mot de passe si fourni
        if (utilisateur.getMotDePasse() != null) {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }
        // rôle par défaut
        if (utilisateur.getRole() == null) {
            utilisateur.setRole(Role.RESPONSABLE);
        }
        return utilisateurRepository.save(utilisateur);
    }

    @Transactional
    public Utilisateur modifierUtilisateur(Long id, Utilisateur utilisateur){
        // Récupération ou exception si non trouvé
        Utilisateur existing = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière introuvable : " + id));

        // Mise à jour des champs simples
        if (utilisateur.getNom() != null) {
            existing.setNom(utilisateur.getNom());
        }
        if (utilisateur.getPrenom() != null) {
            existing.setPrenom(utilisateur.getPrenom());
        }
        if (utilisateur.getEmail() != null) {
            existing.setEmail(utilisateur.getEmail());
        }
        if (utilisateur.getMotDePasse() != null) {
            existing.setMotDePasse(utilisateur.getMotDePasse());
        }
        if (utilisateur.getNumTel() != null) {
            existing.setNumTel(utilisateur.getNumTel());
        }
        if (utilisateur.getDateNaissance() != null) {
            existing.setDateNaissance(utilisateur.getDateNaissance());
        }
        if (utilisateur.getRole() != null) {
            existing.setRole(utilisateur.getRole());
        }
        return utilisateurRepository.save(existing);
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
