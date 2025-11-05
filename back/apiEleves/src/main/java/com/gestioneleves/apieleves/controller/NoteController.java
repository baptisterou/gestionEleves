package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.NoteCreateRequest;
import com.gestioneleves.apieleves.dto.NoteDTO;
import com.gestioneleves.apieleves.dto.NoteUpdateRequest;
import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.mapper.NoteMapper;
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
        Note toSave = NoteMapper.fromCreate(request);
        Note saved = noteService.createNote(toSave);
        NoteDTO dto = NoteMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/note/" + dto.getIdNote())).body(dto);
    }

    @GetMapping
    public List<NoteDTO> getAllNotes(){
        return noteService.getAllNotes().stream().map(NoteMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id){
        return NoteMapper.toDto(noteService.getNoteById(id));
    }

    @PutMapping("/{id}")
    public NoteDTO editNote (@PathVariable Long id, @Valid @RequestBody NoteUpdateRequest request){
        Note current = noteService.getNoteById(id);
        Note updated = NoteMapper.applyUpdate(current, request);
        Note saved = noteService.editNote(id, updated);
        return NoteMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote (@PathVariable Long id){
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
