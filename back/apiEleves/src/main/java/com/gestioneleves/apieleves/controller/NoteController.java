package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public Iterable<Note> geAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping("/notes")
    public Note addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }
}
