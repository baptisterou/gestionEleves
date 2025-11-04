package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Classe;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ClasseMapper {
    private ClasseMapper() {}

    public static ClasseDTO toDto(Classe entity) {
        if (entity == null) return null;
        ClasseDTO dto = new ClasseDTO();
        dto.setIdClasse(entity.getIdClasse());
        dto.setNomClasse(entity.getNomClasse());
        dto.setNiveauClasse(entity.getNiveauClasse());
        dto.setAnneeScolaire(entity.getAnneeScolaire());
        return dto;
    }

    public static List<ClasseDTO> toDtoList(List<Classe> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).map(ClasseMapper::toDto).collect(Collectors.toList());
    }

    public static Classe fromCreate(ClasseDTO req) {
        if (req == null) return null;
        Classe c = new Classe();
        c.setAnneeScolaire(req.getAnneeScolaire());
        c.setNiveauClasse(req.getNiveauClasse());
        c.setNomClasse(req.getNomClasse());
        return c;
    }

    public static void applyUpdate(ClasseDTO req, Classe target) {
        if (req == null || target == null) return;
        if (req.getAnneeScolaire() != null) target.setAnneeScolaire(req.getAnneeScolaire());
        if (req.getNiveauClasse() != null) target.setNiveauClasse(req.getNiveauClasse());
        if (req.getNomClasse() != null) target.setNomClasse(req.getNomClasse());
    }

    public static Classe fromUpdate(ClasseDTO req) {
        if (req == null) return null;
        Classe c = new Classe();
        if (req.getAnneeScolaire() != null) c.setAnneeScolaire(req.getAnneeScolaire());
        if (req.getNiveauClasse() != null) c.setNiveauClasse(req.getNiveauClasse());
        if (req.getNomClasse() != null) c.setNomClasse(req.getNomClasse());
        return c;
    }
}
