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

/**
 * Contrôleur REST exposant les opérations CRUD pour la ressource {@code Note}.
 *
 * Endpoints:
 * - POST   /api/note            : création d'une note
 * - GET    /api/note            : liste de toutes les notes
 * - GET    /api/note/{id}       : détail d'une note
 * - PUT    /api/note/{id}       : mise à jour d'une note
 * - DELETE /api/note/{id}       : suppression d'une note
 *
 * Gestion d'erreurs: voir {@link com.gestioneleves.apieleves.config.ApiExceptionHandler}
 */
@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    /**
     * Crée une nouvelle note.
     *
     * @param request charge utile de création (validée)
     * @return 201 Created avec la note créée et l'en-tête Location
     */
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@Valid @RequestBody NoteCreateRequest request){
        NoteDTO dto = noteService.createNote(request);
        return ResponseEntity.created(URI.create("/api/note/" + dto.getIdNote())).body(dto);
    }

    /**
     * Récupère toutes les notes (non paginé).
     *
     * @return liste des {@link NoteDTO}
     */
    @GetMapping
    public List<NoteDTO> getAllNotes(){
        return noteService.getAllNotes().stream().map(com.gestioneleves.apieleves.mapper.NoteMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Récupère une note par identifiant.
     *
     * @param id identifiant de la note
     * @return la note demandée
     */
    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id){
        return com.gestioneleves.apieleves.mapper.NoteMapper.toDto(noteService.getNoteById(id));
    }

    /**
     * Met à jour une note existante.
     *
     * @param id identifiant de la note
     * @param request champs à mettre à jour
     * @return la note mise à jour
     */
    @PutMapping("/{id}")
    public NoteDTO editNote (@PathVariable Long id, @Valid @RequestBody NoteUpdateRequest request){
        return noteService.editNote(id, request);
    }

    /**
     * Supprime une note.
     *
     * @param id identifiant de la note à supprimer
     * @return 204 No Content si succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote (@PathVariable Long id){
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
