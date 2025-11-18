package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.UtilisateurAdminCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurRoleUpdateRequest;
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

/**
 * Service métier pour la gestion des {@code Utilisateur}.
 *
 * Responsabilités:
 * - Créer des utilisateurs (standard et via ADMIN avec rôle explicite)
 * - Lister/paginer les utilisateurs
 * - Modifier les informations d'un utilisateur et changer son rôle (ADMIN-only)
 * - Garantir l'unicité/normalisation de l'email et l'encodage des mots de passe
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link org.springframework.transaction.annotation.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si l'utilisateur n'existe pas
 * - {@link IllegalArgumentException} en cas d'email déjà utilisé ou de données invalides
 */
@Service
@Transactional
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère tous les utilisateurs (non paginé).
     */
    public List<Utilisateur> getAllUtilisateurs(){
        return utilisateurRepository.findAll();
    }

    /**
     * Récupère les utilisateurs paginés.
     */
    public Page<Utilisateur> getAllUtilisateurs(Pageable pageable){
        return utilisateurRepository.findAll(pageable);
    }

    /**
     * Crée un utilisateur standard à partir d'une requête et retourne un DTO.
     *
     * Le rôle est forcé côté service à RESPONSABLE par sécurité.
     */
    public UtilisateurDTO createUtilisateur(UtilisateurCreateRequest request) {
        Utilisateur toSave = UtilisateurMapper.fromCreate(request);
        Utilisateur saved = createUtilisateur(toSave); // réutilise la logique existante
        return UtilisateurMapper.toDto(saved);
    }

    /**
     * Création ADMIN permettant de spécifier le rôle explicitement.
     *
     * Valide l'unicité de l'email et encode le mot de passe si fourni.
     */
    public UtilisateurDTO createUtilisateurAsAdmin(UtilisateurAdminCreateRequest request) {
        Utilisateur toSave = UtilisateurMapper.fromAdminCreate(request);
        // normaliser email
        if (toSave.getEmail() != null) {
            toSave.setEmail(toSave.getEmail().trim().toLowerCase());
        }
        // unicité email
        utilisateurRepository.findByEmail(toSave.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email déjà utilisé");
        });
        // encoder le mot de passe si fourni
        if (toSave.getMotDePasse() != null) {
            toSave.setMotDePasse(passwordEncoder.encode(toSave.getMotDePasse()));
        }
        Utilisateur saved = utilisateurRepository.save(toSave);
        return UtilisateurMapper.toDto(saved);
    }

    /**
     * Persiste un utilisateur standard après normalisation et validations de base.
     */
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

    /**
     * Met à jour partiellement un utilisateur via DTO de mise à jour et retourne un DTO.
     */
    public UtilisateurDTO modifierUtilisateur(Long id, UtilisateurUpdateRequest request) {
        // Construire un "partial" à partir de la request et réutiliser la logique existante
        Utilisateur part = UtilisateurMapper.fromUpdate(request);
        Utilisateur updated = modifierUtilisateur(id, part);
        return UtilisateurMapper.toDto(updated);
    }

    /**
     * Applique une mise à jour partielle à une entité utilisateur.
     *
     * @throws EntityNotFoundException si l'utilisateur n'existe pas
     * @throws IllegalArgumentException si l'email est déjà utilisé par un autre
     */
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

    /**
     * Change le rôle d'un utilisateur (ADMIN uniquement).
     */
    public UtilisateurDTO updateRole(Long id, UtilisateurRoleUpdateRequest request) {
        Utilisateur existing = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable : " + id));
        existing.setRole(request.role);
        Utilisateur saved = utilisateurRepository.save(existing);
        return UtilisateurMapper.toDto(saved);
    }

    /**
     * Supprime un utilisateur par identifiant.
     */
    public void supprimerUtilisateur(Long id){
        if (!utilisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur introuvable: " + id);
        }
        utilisateurRepository.deleteById(id);
    }

    /**
     * Récupère un utilisateur par identifiant.
     */
    public Utilisateur getUtilisateurById(Long id){
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable: " + id));
    }
}
