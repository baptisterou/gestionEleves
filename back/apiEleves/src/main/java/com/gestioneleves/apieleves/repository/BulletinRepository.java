package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Bulletin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Bulletin
 * Interface qui étend CrudRepository pour bénéficier des opérations CRUD automatiques
 * Spring Data JPA génère automatiquement l'implémentation de cette interface
 * Fournit les méthodes standard : save, findById, findAll, delete, etc.
 */
@Repository // Indique que cette interface est un repository Spring (accès aux données)
public interface BulletinRepository extends CrudRepository<Bulletin, Long> {
    // Cette interface est volontairement vide
    // Spring Data JPA fournit automatiquement les implémentations des méthodes CRUD :
    // - save(Bulletin entity) : sauvegarde un bulletin
    // - findById(Integer id) : trouve un bulletin par son ID
    // - findAll() : retourne tous les bulletins
    // - deleteById(Integer id) : supprime un bulletin par son ID
    // - count() : compte le nombre de bulletins
    // - existsById(Integer id) : vérifie si un bulletin existe par son ID

    // Le générique <Bulletin, Integer> signifie :
    // - Bulletin : le type d'entité géré par ce repository
    // - Integer : le type de la clé primaire de l'entité Bulletin (id_bulletin)
}