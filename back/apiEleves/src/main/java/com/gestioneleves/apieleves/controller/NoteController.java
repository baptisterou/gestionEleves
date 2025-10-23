package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping()
    public Note createNote(@RequestBody Note note){
        return noteService.createNote(note);
    };

    @GetMapping()
    public List<Note> getAllNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public Note modifierNote (@PathVariable Long id, @RequestBody  Note note){
        return noteService.editNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote (Long id){
        noteService.sdeleteNote(id);
    }
}
