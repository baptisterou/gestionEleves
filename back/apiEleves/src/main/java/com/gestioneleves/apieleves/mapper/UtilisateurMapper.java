package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.UtilisateurCreateRequest;
import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.dto.UtilisateurUpdateRequest;
import com.gestioneleves.apieleves.entity.Utilisateur;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class UtilisateurMapper {
    private UtilisateurMapper() {}

    private static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private static Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return java.sql.Date.valueOf(localDate);
    }

    public static UtilisateurDTO toDto(Utilisateur entity) {
        if (entity == null) return null;
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.idUtilisateur = entity.getIdUtilisateur();
        dto.nom = entity.getNom();
        dto.prenom = entity.getPrenom();
        dto.email = entity.getEmail();
        dto.numTel = entity.getNumTel();
        dto.dateNaissance = toLocalDate(entity.getDateNaissance());
        return dto;
    }

    public static List<UtilisateurDTO> toDtoList(List<Utilisateur> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).map(UtilisateurMapper::toDto).collect(Collectors.toList());
    }

    public static Utilisateur fromCreate(UtilisateurCreateRequest req) {
        if (req == null) return null;
        Utilisateur u = new Utilisateur();
        u.setNom(req.nom);
        u.setPrenom(req.prenom);
        u.setEmail(req.email);
        u.setMotDePasse(req.motDePasse);
        u.setDateNaissance(toDate(req.dateNaissance));
        u.setNumTel(req.numTel);
        return u;
    }

    public static void applyUpdate(UtilisateurUpdateRequest req, Utilisateur target) {
        if (req == null || target == null) return;
        if (req.nom != null) target.setNom(req.nom);
        if (req.prenom != null) target.setPrenom(req.prenom);
        if (req.email != null) target.setEmail(req.email);
        if (req.motDePasse != null) target.setMotDePasse(req.motDePasse);
        if (req.dateNaissance != null) target.setDateNaissance(toDate(req.dateNaissance));
        if (req.numTel != null) target.setNumTel(req.numTel);
    }

    public static Utilisateur fromUpdate(UtilisateurUpdateRequest req) {
        if (req == null) return null;
        Utilisateur u = new Utilisateur();
        if (req.nom != null) u.setNom(req.nom);
        if (req.prenom != null) u.setPrenom(req.prenom);
        if (req.email != null) u.setEmail(req.email);
        if (req.motDePasse != null) u.setMotDePasse(req.motDePasse);
        if (req.dateNaissance != null) u.setDateNaissance(toDate(req.dateNaissance));
        if (req.numTel != null) u.setNumTel(req.numTel);
        return u;
    }
}
