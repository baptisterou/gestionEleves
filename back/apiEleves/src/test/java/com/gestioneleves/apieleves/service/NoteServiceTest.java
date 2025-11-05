package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    private NoteRepository repository;
    private NoteService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(NoteRepository.class);
        service = new NoteService(repository);
    }

    @Test
    void createNote_ok_whenValid() {
        Note n = new Note();
        n.setDateNote(LocalDate.now());
        n.setCoefNote(2f);
        n.setValeurNote(15f);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Note saved = service.createNote(n);
        assertEquals(2f, saved.getCoefNote());
        assertEquals(15f, saved.getValeurNote());
    }

    @Test
    void createNote_throws_whenCoefInvalid() {
        Note n = new Note();
        n.setDateNote(LocalDate.now());
        n.setCoefNote(0f);
        n.setValeurNote(10f);
        assertThrows(IllegalArgumentException.class, () -> service.createNote(n));
    }

    @Test
    void createNote_throws_whenValeurInvalid() {
        Note n = new Note();
        n.setDateNote(LocalDate.now());
        n.setCoefNote(1f);
        n.setValeurNote(25f);
        assertThrows(IllegalArgumentException.class, () -> service.createNote(n));
    }

    @Test
    void editNote_updatesFields_whenValidAndExists() {
        Note current = new Note();
        current.setDateNote(LocalDate.now());
        current.setCoefNote(1f);
        current.setValeurNote(10f);
        when(repository.findById(5L)).thenReturn(Optional.of(current));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Note patch = new Note();
        patch.setCoefNote(3f);
        patch.setValeurNote(18f);

        Note saved = service.editNote(5L, patch);
        assertEquals(3f, saved.getCoefNote());
        assertEquals(18f, saved.getValeurNote());
    }

    @Test
    void editNote_throws_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.editNote(99L, new Note()));
    }

    @Test
    void deleteNote_throws_whenNotExists() {
        when(repository.existsById(77L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteNote(77L));
    }
}
