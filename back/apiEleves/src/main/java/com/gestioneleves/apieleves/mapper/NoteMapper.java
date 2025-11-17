package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.NoteCreateRequest;
import com.gestioneleves.apieleves.dto.NoteDTO;
import com.gestioneleves.apieleves.dto.NoteUpdateRequest;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Note;

import java.time.LocalDate;

public class NoteMapper {

    public static Note fromCreate(NoteCreateRequest req) {
        Note n = new Note();
        n.setDateNote(req.getDateNote());
        n.setCoefNote(req.getCoefNote());
        n.setValeurNote(req.getValeurNote());
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            n.setEleve(e);
        }
        if (req.getIdMatiere() != null) {
            Matiere m = new Matiere();
            m.setIdMatiere(req.getIdMatiere());
            n.setMatiere(m);
        }
        if (req.getIdBulletin() != null) {
            Bulletin b = new Bulletin();
            b.setIdBulletin(req.getIdBulletin());
            n.setBulletin(b);
        }
        return n;
    }

    public static Note applyUpdate(Note target, NoteUpdateRequest req) {
        if (req.getDateNote() != null) target.setDateNote(req.getDateNote());
        if (req.getCoefNote() != null) target.setCoefNote(req.getCoefNote());
        if (req.getValeurNote() != null) target.setValeurNote(req.getValeurNote());
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            target.setEleve(e);
        }
        if (req.getIdMatiere() != null) {
            Matiere m = new Matiere();
            m.setIdMatiere(req.getIdMatiere());
            target.setMatiere(m);
        }
        if (req.getIdBulletin() != null) {
            Bulletin b = new Bulletin();
            b.setIdBulletin(req.getIdBulletin());
            target.setBulletin(b);
        }
        return target;
    }

    public static NoteDTO toDto(Note n) {
        NoteDTO dto = new NoteDTO();
        dto.setIdNote(n.getIdNote());
        dto.setDateNote(n.getDateNote());
        dto.setCoefNote(n.getCoefNote());
        dto.setValeurNote(n.getValeurNote());
        if (n.getEleve() != null) dto.setIdEleve(n.getEleve().getIdEleve());
        if (n.getMatiere() != null) dto.setIdMatiere(n.getMatiere().getIdMatiere());
        if (n.getBulletin() != null) dto.setIdBulletin(n.getBulletin().getIdBulletin());
        return dto;
    }
}
