package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Inscription;
import com.gestioneleves.apieleves.repository.InscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InscriptionServiceTest {

    private InscriptionRepository repository;
    private InscriptionService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(InscriptionRepository.class);
        service = new InscriptionService(repository);
    }

    @Test
    void deleteInscription_throws_whenNotExists() {
        when(repository.existsById(77L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteInscription(77L));
    }

    @Test
    void createInscription_ok() {
        Inscription i = new Inscription();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Inscription saved = service.createInscription(i);
        assertNotNull(saved);
    }

    @Test
    void getAllInscriptions_ok() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Inscription(), new Inscription()));
        List<Inscription> list = service.getAllInscriptions();
        assertEquals(2, list.size());
    }
}
