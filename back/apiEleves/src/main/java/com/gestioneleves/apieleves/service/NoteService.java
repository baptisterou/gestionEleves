package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.repository.NoteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Iterable<Note> getAllNotes() {

        return noteRepository.findAll();
    }

    public Optional<Note> getNote(Long id) {
        return noteRepository.findById(id); }

    public Note addNote(Note note) {

        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id); }
}
