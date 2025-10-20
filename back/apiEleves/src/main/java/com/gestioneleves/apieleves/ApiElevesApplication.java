package com.gestioneleves.apieleves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Spring Boot
 * Point d'entrée qui démarre toute l'application
 *
 * Responsabilités :
 * - Démarrer le conteneur Spring
 * - Scanner et configurer automatiquement tous les composants
 * - Lancer le serveur web embarqué (Tomcat)
 * - Activer la configuration automatique de Spring Boot
 */
@SpringBootApplication // Annotation magique qui combine :
// - @Configuration : classe de configuration
// - @EnableAutoConfiguration : configuration automatique
// - @ComponentScan : scan des composants dans le package et sous-packages
public class ApiElevesApplication {

    /**
     * Méthode principale - point de départ de l'application
     * C'est la première méthode exécutée quand l'application démarre
     *
     * @param args Arguments de la ligne de commande (optionnels)
     */
    public static void main(String[] args) {
        // Démarre l'application Spring Boot
        // - Crée le contexte Spring
        // - Scanne et initialise tous les beans (@Service, @Repository, @Controller, etc.)
        // - Démarre le serveur web sur le port 8080 par défaut
        // - Rend l'application prête à recevoir des requêtes HTTP
        SpringApplication.run(ApiElevesApplication.class, args);
    }
}