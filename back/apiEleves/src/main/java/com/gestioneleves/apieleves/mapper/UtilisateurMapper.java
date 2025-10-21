package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.UtilisateurDto;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class UtilisateurMapper {

    public static Utilisateur dtoToEntity(UtilisateurDto dto){

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setIdUtilisateur(dto.getIdUtilisateur());

        utilisateur.setDateNaissance(dto.getDateNaissance());

        utilisateur.setEmail(dto.getEmail());

        utilisateur.setNumTel(dto.getNumTel());

        utilisateur.setNom(dto.getNom());

        utilisateur.setPrenom(dto.getPrenom());

        utilisateur.setRole(dto.getRole());

        return utilisateur;
    }

    public static UtilisateurDto entityToDto(Utilisateur entity) {

        UtilisateurDto dto = new UtilisateurDto();

        dto.setIdUtilisateur(entity.getIdUtilisateur());

        dto.setDateNaissance(entity.getDateNaissance());

        dto.setEmail(entity.getEmail());

        dto.setNumTel(entity.getNumTel());

        dto.setNom(entity.getNom());

        dto.setPrenom(entity.getPrenom());

        dto.setRole(entity.getRole());

        return dto;
    }
}
