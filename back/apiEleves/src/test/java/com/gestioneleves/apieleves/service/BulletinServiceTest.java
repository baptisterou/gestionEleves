package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BulletinServiceTest {

    private BulletinRepository repository;
    private BulletinService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(BulletinRepository.class);
        service = new BulletinService(repository);
    }

    @Test
    void createBulletin_ok_whenValid() {
        Bulletin b = new Bulletin();
        b.setTrimestreBulletin(1);
        b.setAnneeBulletin(Year.now().getValue());
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Bulletin saved = service.createBulletin(b);
        assertEquals(1, saved.getTrimestreBulletin());
    }

    @Test
    void createBulletin_throws_whenTrimestreInvalid() {
        Bulletin b = new Bulletin();
        b.setTrimestreBulletin(5);
        b.setAnneeBulletin(Year.now().getValue());
        assertThrows(IllegalArgumentException.class, () -> service.createBulletin(b));
    }

    @Test
    void createBulletin_throws_whenAnneeInvalid() {
        Bulletin b = new Bulletin();
        b.setTrimestreBulletin(2);
        b.setAnneeBulletin(1999);
        assertThrows(IllegalArgumentException.class, () -> service.createBulletin(b));
    }

    @Test
    void editBulletin_updatesFields_andValidates() {
        Bulletin current = new Bulletin();
        current.setTrimestreBulletin(1);
        current.setAnneeBulletin(Year.now().getValue());
        current.setCommentaire("old");
        when(repository.findById(10L)).thenReturn(Optional.of(current));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Bulletin patch = new Bulletin();
        patch.setTrimestreBulletin(3);
        patch.setAnneeBulletin(Year.now().getValue());
        patch.setCommentaire("new");

        Bulletin saved = service.editBulletin(10L, patch);
        assertEquals(3, saved.getTrimestreBulletin());
        assertEquals("new", saved.getCommentaire());
    }

    @Test
    void deleteBulletin_throws_whenNotExists() {
        when(repository.existsById(77L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteBulletin(77L));
    }
}
