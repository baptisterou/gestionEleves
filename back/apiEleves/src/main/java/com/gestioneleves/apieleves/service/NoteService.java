package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import com.gestioneleves.apieleves.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private BulletinRepository bulletinRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public Note createNote (Note note){
        return noteRepository.save(note);
    }

    public Note editNote(Long id, Note note){
        // Récupération ou exception si non trouvé
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière introuvable : " + id));

        // Mise à jour des champs simples
        if (note.getDateNote() != null) {
            existing.setDateNote(note.getDateNote());
        }
        if (note.getCoefNote() > 0) {
            existing.setCoefNote(note.getCoefNote());
        }
        if (note.getValeurNote() >=  0 || note.getValeurNote() <= 20) {
            existing.setValeurNote(note.getValeurNote());
        }

        // Mise à jour de l'objet lié
        if (note.getBulletin() != null && note.getBulletin().getIdBulletin() != null) {
            Bulletin bulletin = bulletinRepository.findById(note.getBulletin().getIdBulletin())
                    .orElseThrow(() -> new EntityNotFoundException("Bulletin introuvable : " + note.getBulletin().getIdBulletin()));
            existing.setBulletin(bulletin);
        }

        // Sauvegarde et retour
        return noteRepository.save(existing);
    }

    public void deleteNote(Long id){
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note introuvable: " + id);
        }
        noteRepository.deleteById(id);
    }

    public Note getNoteById(Long id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note introuvable: " + id));
    }
}
