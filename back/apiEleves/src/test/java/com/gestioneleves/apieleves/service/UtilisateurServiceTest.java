package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Role;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UtilisateurServiceTest {

    private UtilisateurRepository repository;
    private PasswordEncoder encoder;
    private UtilisateurService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(UtilisateurRepository.class);
        encoder = Mockito.mock(PasswordEncoder.class);
        service = new UtilisateurService(repository, encoder);
    }

    @Test
    void createUtilisateur_encodesPassword_andSetsDefaultRole() {
        Utilisateur u = new Utilisateur();
        u.setNom("Doe");
        u.setPrenom("John");
        u.setEmail("john@ex.com");
        u.setMotDePasse("plain");
        u.setDateNaissance(new Date());
        u.setNumTel("0102030405");

        when(repository.findByEmail("john@ex.com")).thenReturn(Optional.empty());
        when(encoder.encode("plain")).thenReturn("encoded");
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Utilisateur saved = service.createUtilisateur(u);
        assertEquals("encoded", saved.getMotDePasse());
        assertEquals(Role.RESPONSABLE, saved.getRole());
    }

    @Test
    void createUtilisateur_throws_whenEmailAlreadyUsed() {
        Utilisateur u = new Utilisateur();
        u.setEmail("exists@ex.com");
        when(repository.findByEmail("exists@ex.com")).thenReturn(Optional.of(new Utilisateur()));
        assertThrows(IllegalArgumentException.class, () -> service.createUtilisateur(u));
    }

    @Test
    void modifierUtilisateur_updatesProvidedFields_andChecksEmailUniqueness() {
        Utilisateur current = new Utilisateur();
        current.setIdUtilisateur(1L);
        current.setEmail("a@a.com");
        when(repository.findById(1L)).thenReturn(Optional.of(current));
        when(repository.findByEmail("b@b.com")).thenReturn(Optional.empty());
        when(encoder.encode("newpass")).thenReturn("enc");
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Utilisateur patch = new Utilisateur();
        patch.setNom("New");
        patch.setPrenom("Name");
        patch.setEmail("b@b.com");
        patch.setMotDePasse("newpass");
        patch.setRole(Role.ADMIN);

        Utilisateur saved = service.modifierUtilisateur(1L, patch);
        assertEquals("New", saved.getNom());
        assertEquals("Name", saved.getPrenom());
        assertEquals("b@b.com", saved.getEmail());
        assertEquals("enc", saved.getMotDePasse());
        assertEquals(Role.ADMIN, saved.getRole());
    }

    @Test
    void modifierUtilisateur_throws_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.modifierUtilisateur(99L, new Utilisateur()));
    }

    @Test
    void supprimerUtilisateur_throws_whenNotExists() {
        when(repository.existsById(42L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.supprimerUtilisateur(42L));
    }
}
