package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Matiere
 * Interface qui étend CrudRepository pour bénéficier des opérations CRUD automatiques
 *
 * Spring Data JPA génère automatiquement l'implémentation de cette interface
 * Fournit les méthodes standard : save, findById, findAll, delete, etc.
 */
@Repository // Indique que cette interface est un repository Spring (accès aux données)
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    // Cette interface est volontairement vide
    // Spring Data JPA fournit automatiquement les implémentations des méthodes CRUD :
    // - save(Matiere entity) : sauvegarde une matière
    // - findById(Integer id) : trouve une matière par son ID
    // - findAll() : retourne toutes les matières
    // - deleteById(Integer id) : supprime une matière par son ID
    // - count() : compte le nombre de matières
    // - existsById(Integer id) : vérifie si une matière existe par son ID

    // Le générique <Matiere, Integer> signifie :
    // - Matiere : le type d'entité géré par ce repository
    // - Integer : le type de la clé primaire de l'entité Matiere (id_matiere)
}
