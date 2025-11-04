package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClasseServiceTest {

    private ClasseRepository repository;
    private ClasseService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(ClasseRepository.class);
        service = new ClasseService(repository);
    }

    @Test
    void getAllClasses_pagination_ok() {
        PageRequest pr = PageRequest.of(0, 10);
        when(repository.findAll(pr)).thenReturn(new PageImpl<>(Collections.emptyList(), pr, 0));
        Page<Classe> page = service.getAllClasses(pr);
        assertNotNull(page);
        assertEquals(0, page.getTotalElements());
    }

    @Test
    void editClasse_updatesProvidedFields_whenExists() {
        Classe current = new Classe();
        current.setIdClasse(1L);
        current.setNomClasse("A");
        when(repository.findById(1L)).thenReturn(Optional.of(current));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Classe patch = new Classe();
        patch.setNomClasse("B");
        Classe saved = service.editClasse(1L, patch);
        assertEquals("B", saved.getNomClasse());
    }

    @Test
    void editClasse_throws_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.editClasse(99L, new Classe()));
    }

    @Test
    void deleteClasse_throws_whenNotExists() {
        when(repository.existsById(77L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteClasse(77L));
    }
}
