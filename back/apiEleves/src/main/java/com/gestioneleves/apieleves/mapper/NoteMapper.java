package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.NoteCreateRequest;
import com.gestioneleves.apieleves.dto.NoteDTO;
import com.gestioneleves.apieleves.dto.NoteUpdateRequest;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Note;

import java.time.LocalDate;
import java.util.Date;

public class NoteMapper {

    private static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private static Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return java.sql.Date.valueOf(localDate);
    }

    public static Note fromCreate(NoteCreateRequest req) {
        Note n = new Note();
        n.setDateNote(toDate(req.getDateNote()));
        n.setCoefNote(req.getCoefNote());
        n.setValeurNote(req.getValeurNote());
        if (req.getEleveId() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getEleveId());
            n.setEleve(e);
        }
        if (req.getMatiereId() != null) {
            Matiere m = new Matiere();
            m.setIdMatiere(req.getMatiereId());
            n.setMatiere(m);
        }
        if (req.getBulletinId() != null) {
            Bulletin b = new Bulletin();
            b.setIdBulletin(req.getBulletinId());
            n.setBulletin(b);
        }
        return n;
    }

    public static Note applyUpdate(Note target, NoteUpdateRequest req) {
        if (req.getDateNote() != null) target.setDateNote(toDate(req.getDateNote()));
        if (req.getCoefNote() != null) target.setCoefNote(req.getCoefNote());
        if (req.getValeurNote() != null) target.setValeurNote(req.getValeurNote());
        if (req.getEleveId() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getEleveId());
            target.setEleve(e);
        }
        if (req.getMatiereId() != null) {
            Matiere m = new Matiere();
            m.setIdMatiere(req.getMatiereId());
            target.setMatiere(m);
        }
        if (req.getBulletinId() != null) {
            Bulletin b = new Bulletin();
            b.setIdBulletin(req.getBulletinId());
            target.setBulletin(b);
        }
        return target;
    }

    public static NoteDTO toDto(Note n) {
        NoteDTO dto = new NoteDTO();
        dto.setIdNote(n.getIdNote());
        dto.setDateNote(toLocalDate(n.getDateNote()));
        dto.setCoefNote(n.getCoefNote());
        dto.setValeurNote(n.getValeurNote());
        if (n.getEleve() != null) dto.setEleveId(n.getEleve().getIdEleve());
        if (n.getMatiere() != null) dto.setMatiereId(n.getMatiere().getIdMatiere());
        if (n.getBulletin() != null) dto.setBulletinId(n.getBulletin().getIdBulletin());
        return dto;
    }
}
