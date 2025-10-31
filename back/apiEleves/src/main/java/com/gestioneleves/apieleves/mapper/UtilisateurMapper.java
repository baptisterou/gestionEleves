package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurGestionDTO;
import com.gestioneleves.apieleves.entity.Utilisateur;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class UtilisateurMapper {
    private UtilisateurMapper() {}

    public static UtilisateurDTO toDto(Utilisateur entity) {
        if (entity == null) return null;
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setIdUtilisateur(entity.getIdUtilisateur());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        return dto;
    }

    public static List<UtilisateurDTO> toDtoList(List<Utilisateur> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).map(UtilisateurMapper::toDto).collect(Collectors.toList());
    }

    public static Utilisateur fromCreate(UtilisateurGestionDTO req) {
        if (req == null) return null;
        Utilisateur u = new Utilisateur();
        u.setNom(req.getNom());
        u.setPrenom(req.getPrenom());
        u.setEmail(req.getEmail());
        u.setMotDePasse(req.getMotDePasse());
        u.setDateNaissance(req.getDateNaissance());
        u.setNumTel(req.getNumTel());
        if (req.getRole() != null) u.setRole(req.getRole());
        return u;
    }

    public static void applyUpdate(UtilisateurGestionDTO req, Utilisateur target) {
        if (req == null || target == null) return;
        if (req.getNom() != null) target.setNom(req.getNom());
        if (req.getPrenom() != null) target.setPrenom(req.getPrenom());
        if (req.getEmail() != null) target.setEmail(req.getEmail());
        if (req.getMotDePasse() != null) target.setMotDePasse(req.getMotDePasse());
        if (req.getDateNaissance() != null) target.setDateNaissance(req.getDateNaissance());
        if (req.getNumTel() != null) target.setNumTel(req.getNumTel());
        if (req.getRole() != null) target.setRole(req.getRole());
    }

    public static Utilisateur fromUpdate(UtilisateurGestionDTO req) {
        if (req == null) return null;
        Utilisateur u = new Utilisateur();
        if (req.getNom() != null) u.setNom(req.getNom());
        if (req.getPrenom() != null) u.setPrenom(req.getPrenom());
        if (req.getEmail() != null) u.setEmail(req.getEmail());
        if (req.getMotDePasse() != null) u.setMotDePasse(req.getMotDePasse());
        if (req.getDateNaissance() != null) u.setDateNaissance(req.getDateNaissance());
        if (req.getNumTel() != null) u.setNumTel(req.getNumTel());
        if (req.getRole() != null) u.setRole(req.getRole());
        return u;
    }
}
