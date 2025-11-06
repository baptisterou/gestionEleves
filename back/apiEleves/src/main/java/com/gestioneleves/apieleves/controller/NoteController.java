package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.NoteCreateRequest;
import com.gestioneleves.apieleves.dto.NoteDTO;
import com.gestioneleves.apieleves.dto.NoteUpdateRequest;
import com.gestioneleves.apieleves.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@Valid @RequestBody NoteCreateRequest request){
        NoteDTO dto = noteService.createNote(request);
        return ResponseEntity.created(URI.create("/api/note/" + dto.getIdNote())).body(dto);
    }

    @GetMapping
    public List<NoteDTO> getAllNotes(){
        return noteService.getAllNotes().stream().map(com.gestioneleves.apieleves.mapper.NoteMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id){
        return com.gestioneleves.apieleves.mapper.NoteMapper.toDto(noteService.getNoteById(id));
    }

    @PutMapping("/{id}")
    public NoteDTO editNote (@PathVariable Long id, @Valid @RequestBody NoteUpdateRequest request){
        return noteService.editNote(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote (@PathVariable Long id){
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
