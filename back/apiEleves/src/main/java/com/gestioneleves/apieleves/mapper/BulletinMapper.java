package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.entity.Bulletin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BulletinMapper {
    private BulletinMapper() {}

    public static BulletinDTO toDto(Bulletin entity) {
        if (entity == null) return null;
        BulletinDTO dto = new BulletinDTO();
        dto.setIdBulletin(entity.getIdBulletin());
        dto.setCommentaire(entity.getCommentaire());
        dto.setAnneeBulletin(entity.getAnneeBulletin());
        dto.setTrimestreBulletin(entity.getTrimestreBulletin());
        return dto;
    }

    public static List<BulletinDTO> toDtoList(List<Bulletin> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).map(BulletinMapper::toDto).collect(Collectors.toList());
    }

    public static Bulletin fromCreate(BulletinDTO req) {
        if (req == null) return null;
        Bulletin b = new Bulletin();
        b.setCommentaire(req.getCommentaire());
        b.setAnneeBulletin(req.getAnneeBulletin());
        b.setTrimestreBulletin(req.getTrimestreBulletin());
        return b;
    }

    public static void applyUpdate(BulletinDTO req, Bulletin target) {
        if (req == null || target == null) return;
        if (req.getCommentaire() != null) target.setCommentaire(req.getCommentaire());
        if (req.getAnneeBulletin() != null) target.setAnneeBulletin(req.getAnneeBulletin());
        if (req.getTrimestreBulletin() != null) target.setTrimestreBulletin(req.getTrimestreBulletin());
    }

    public static Bulletin fromUpdate(BulletinDTO req) {
        if (req == null) return null;
        Bulletin b = new Bulletin();
        if (req.getCommentaire() != null) b.setCommentaire(req.getCommentaire());
        if (req.getAnneeBulletin() != null) b.setAnneeBulletin(req.getAnneeBulletin());
        if (req.getTrimestreBulletin() != null) b.setTrimestreBulletin(req.getTrimestreBulletin());
        return b;
    }
}
