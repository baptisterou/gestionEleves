package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

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
        Optional<Note> entite = noteRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Note introuvable: " + id);
        }
        if (note.getDateNote() != null) {
            entite.get().setDateNote(note.getDateNote());
        }
        if (note.getCoefNote() > 0) {
            entite.get().setCoefNote(note.getCoefNote());
        }
        if (note.getValeurNote() >=  0 || note.getValeurNote() <= 20) {
            entite.get().setValeurNote(note.getValeurNote());
        }
        return noteRepository.save(entite.get());
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
