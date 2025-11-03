package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public Note createNote (Note note){
        validateNote(note);
        return noteRepository.save(note);
    }

    public Note editNote(Long id, Note note){
        Optional<Note> entiteOpt = noteRepository.findById(id);
        if (!entiteOpt.isPresent()) {
            throw new EntityNotFoundException("Note introuvable: " + id);
        }
        Note entite = entiteOpt.get();
        if (note.getDateNote() != null) {
            entite.setDateNote(note.getDateNote());
        }
        if (note.getCoefNote() > 0) {
            entite.setCoefNote(note.getCoefNote());
        }
        if (note.getValeurNote() >= 0 && note.getValeurNote() <= 20) {
            entite.setValeurNote(note.getValeurNote());
        }
        // revalidate after applying changes
        validateNote(entite);
        return noteRepository.save(entite);
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

    private void validateNote(Note n) {
        if (n.getCoefNote() <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être > 0");
        }
        if (n.getValeurNote() < 0 || n.getValeurNote() > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20");
        }
    }
}
