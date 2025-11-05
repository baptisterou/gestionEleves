package com.gestioneleves.apieleves.config;

import com.gestioneleves.apieleves.entity.Role;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdminUser(UtilisateurRepository utilisateurRepository,
                                           PasswordEncoder passwordEncoder) {
        return args -> {
            final String adminEmail = "admin@admin.com"; // identifiant de connexion
            final String adminPassword = "admin";        // mot de passe de test

            if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {
                Utilisateur admin = new Utilisateur();
                admin.setNom("Admin");
                admin.setPrenom("Admin");
                admin.setEmail(adminEmail);
                admin.setMotDePasse(passwordEncoder.encode(adminPassword));
                admin.setDateNaissance(LocalDate.now());
                admin.setNumTel("0000000000");
                admin.setRole(Role.ADMIN);
                utilisateurRepository.save(admin);
                System.out.println("[INIT] Utilisateur ADMIN créé: " + adminEmail + " / " + adminPassword);
            } else {
                System.out.println("[INIT] Utilisateur ADMIN déjà présent: " + adminEmail);
            }
        };
    }
}
