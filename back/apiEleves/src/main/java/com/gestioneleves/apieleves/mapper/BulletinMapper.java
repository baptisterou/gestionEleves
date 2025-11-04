package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.BulletinCreateRequest;
import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.dto.BulletinUpdateRequest;
import com.gestioneleves.apieleves.entity.Bulletin;

public class BulletinMapper {

    public static Bulletin fromCreate(BulletinCreateRequest req) {
        Bulletin b = new Bulletin();
        b.setTrimestreBulletin(req.getTrimestreBulletin());
        if (req.getAnneeBulletin() != null) {
            b.setAnneeBulletin(req.getAnneeBulletin());
        }
        b.setCommentaire(req.getCommentaire());
        return b;
    }

    public static Bulletin applyUpdate(Bulletin target, BulletinUpdateRequest req) {
        if (req.getTrimestreBulletin() != null) target.setTrimestreBulletin(req.getTrimestreBulletin());
        if (req.getAnneeBulletin() != null) target.setAnneeBulletin(req.getAnneeBulletin());
        if (req.getCommentaire() != null) target.setCommentaire(req.getCommentaire());
        return target;
    }

    public static BulletinDTO toDto(Bulletin b) {
        BulletinDTO dto = new BulletinDTO();
        dto.setIdBulletin(b.getIdBulletin());
        dto.setTrimestreBulletin(b.getTrimestreBulletin());
        dto.setAnneeBulletin(b.getAnneeBulletin());
        dto.setCommentaire(b.getCommentaire());
        return dto;
    }
}
