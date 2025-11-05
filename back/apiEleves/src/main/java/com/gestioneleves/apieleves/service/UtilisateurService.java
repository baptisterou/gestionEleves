package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.UtilisateurCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurUpdateRequest;
import com.gestioneleves.apieleves.entity.Role;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.UtilisateurMapper;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    // Variante contrôleur-friendly: le service accepte la request et renvoie le DTO
    @Transactional
    public UtilisateurDTO createUtilisateur(UtilisateurCreateRequest request) {
        Utilisateur toSave = UtilisateurMapper.fromCreate(request);
        Utilisateur saved = createUtilisateur(toSave); // réutilise la logique existante
        return UtilisateurMapper.toDto(saved);
    }

    @Transactional
    public Utilisateur createUtilisateur (Utilisateur utilisateur){
        // normaliser email
        if (utilisateur.getEmail() != null) {
            utilisateur.setEmail(utilisateur.getEmail().trim().toLowerCase());
        }
        // unicité email
        utilisateurRepository.findByEmail(utilisateur.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email déjà utilisé");
        });
        // encoder le mot de passe si fourni
        if (utilisateur.getMotDePasse() != null) {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }
        // Forcer le rôle par défaut (ne pas faire confiance à l'entrée client)
        utilisateur.setRole(Role.RESPONSABLE);
        return utilisateurRepository.save(utilisateur);
    }

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    @Transactional
    public UtilisateurDTO modifierUtilisateur(Long id, UtilisateurUpdateRequest request) {
        // Construire un "partial" à partir de la request et réutiliser la logique existante
        Utilisateur part = UtilisateurMapper.fromUpdate(request);
        Utilisateur updated = modifierUtilisateur(id, part);
        return UtilisateurMapper.toDto(updated);
    }

    @Transactional
    public Utilisateur modifierUtilisateur(Long id, Utilisateur utilisateur){
        // Récupération ou exception si non trouvé
        Utilisateur existing = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable : " + id));

        // Mise à jour des champs simples
        if (utilisateur.getNom() != null) {
            existing.setNom(utilisateur.getNom());
        }
        if (utilisateur.getPrenom() != null) {
            existing.setPrenom(utilisateur.getPrenom());
        }
        if (utilisateur.getEmail() != null) {
            String newEmail = utilisateur.getEmail().trim().toLowerCase();
            utilisateurRepository.findByEmail(newEmail)
                    .filter(u -> !u.getIdUtilisateur().equals(id))
                    .ifPresent(u -> { throw new IllegalArgumentException("Email déjà utilisé"); });
            existing.setEmail(newEmail);
        }
        if (utilisateur.getMotDePasse() != null) {
            existing.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        }
        if (utilisateur.getNumTel() != null) {
            existing.setNumTel(utilisateur.getNumTel());
        }
        if (utilisateur.getDateNaissance() != null) {
            existing.setDateNaissance(utilisateur.getDateNaissance());
        }
        // Ne pas permettre le changement de rôle via cet endpoint standard
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
