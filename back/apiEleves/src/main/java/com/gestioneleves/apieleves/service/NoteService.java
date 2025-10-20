package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.model.Note;
import com.gestioneleves.apieleves.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Iterable<Note> getNotes() {
        return noteRepository.findAll();
    }

    public Note ajouterNote(Note note) {
        return noteRepository.save(note);
    }
}
