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
        Optional<Utilisateur> entiteOpt = utilisateurRepository.findById(id);
        if (!entiteOpt.isPresent()) {
            throw new EntityNotFoundException("Utilisateur introuvable: " + id);
        }
        Utilisateur entite = entiteOpt.get();
        if (utilisateur.getNom() != null) {
            entite.setNom(utilisateur.getNom());
        }
        if (utilisateur.getPrenom() != null) {
            entite.setPrenom(utilisateur.getPrenom());
        }
        if (utilisateur.getEmail() != null) {
            // vérifier l'unicité si l'email change
            utilisateurRepository.findByEmail(utilisateur.getEmail())
                    .filter(u -> !u.getIdUtilisateur().equals(id))
                    .ifPresent(u -> { throw new IllegalArgumentException("Email déjà utilisé"); });
            entite.setEmail(utilisateur.getEmail());
        }
        if (utilisateur.getMotDePasse() != null) {
            entite.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }
        if (utilisateur.getNumTel() != null) {
            entite.setNumTel(utilisateur.getNumTel());
        }
        if (utilisateur.getDateNaissance() != null) {
            entite.setDateNaissance(utilisateur.getDateNaissance());
        }
        if (utilisateur.getRole() != null) {
            entite.setRole(utilisateur.getRole());
        }
        return utilisateurRepository.save(entite);
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
