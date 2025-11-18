package com.gestioneleves.apieleves.config;

import com.gestioneleves.apieleves.entity.Role;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * Configuration responsable de l'initialisation de données au démarrage de l'application.
 *
 * Actuellement, crée un compte administrateur par défaut si aucun utilisateur avec l'email
 * "admin@admin.com" n'existe. Les identifiants sont affichés en console afin de faciliter
 * les tests en environnement de développement.
 *
 * Attention: ne pas utiliser ces identifiants en production. Préférez une stratégie
 * de provisionnement sécurisée (migrations, variables d'environnement, vault, etc.).
 */
@Configuration
public class DataInitializer {

    @Bean
    /**
     * Runner d'initialisation exécuté au démarrage de l'application.
     *
     * @param utilisateurRepository accès aux utilisateurs
     * @param passwordEncoder encodeur de mots de passe
     * @return une lambda qui crée l'administrateur par défaut si nécessaire
     */
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
