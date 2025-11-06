package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.EleveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EleveServiceTest {

    private EleveRepository repository;
    private EleveService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(EleveRepository.class);
        service = new EleveService(repository);
    }

    @Test
    void getAllEleves_pageable_ok() {
        Eleve e1 = new Eleve(); e1.setIdEleve(1L);
        Eleve e2 = new Eleve(); e2.setIdEleve(2L);
        when(repository.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(Arrays.asList(e1, e2)));
        Page<Eleve> page = service.getAllEleves(PageRequest.of(0, 20));
        assertEquals(2, page.getContent().size());
    }

    @Test
    void editEleve_updatesOnlyProvidedFields() {
        Eleve current = new Eleve();
        current.setIdEleve(10L);
        current.setNom("Old");
        current.setPrenom("Name");
        current.setDateNaissance(LocalDate.now());
        when(repository.findById(10L)).thenReturn(Optional.of(current));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Eleve patch = new Eleve();
        patch.setNom("New"); // only change nom
        Eleve saved = service.editEleve(10L, patch);
        assertEquals("New", saved.getNom());
        assertEquals("Name", saved.getPrenom()); // unchanged
    }

    @Test
    void editEleve_throws_whenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.editEleve(999L, new Eleve()));
    }

    @Test
    void deleteEleve_throws_whenNotExists() {
        when(repository.existsById(77L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteEleve(77L));
    }

    @Test
    void getEleveById_throws_whenNotFound() {
        when(repository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getEleveById(5L));
    }
}
