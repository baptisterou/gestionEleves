package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Inscrire;
import com.gestioneleves.apieleves.entity.InscrireId;
import com.gestioneleves.apieleves.repository.InscrireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InscrireServiceTest {

    private InscrireRepository repository;
    private InscrireService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(InscrireRepository.class);
        service = new InscrireService(repository);
    }

    @Test
    void deleteInscription_throws_whenNotExists() {
        InscrireId id = new InscrireId(1L, 2L);
        when(repository.existsById(id)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteInscription(id));
    }

    @Test
    void createInscription_ok() {
        Inscrire i = new Inscrire();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Inscrire saved = service.createInscription(i);
        assertNotNull(saved);
    }

    @Test
    void getAllInscriptions_ok() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Inscrire(), new Inscrire()));
        List<Inscrire> list = service.getAllInscriptions();
        assertEquals(2, list.size());
    }
}
