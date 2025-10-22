package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Eleve
 * Interface qui étend CrudRepository pour bénéficier des opérations CRUD automatiques
 *
 * Spring Data JPA génère automatiquement l'implémentation de cette interface
 * Fournit les méthodes standard : save, findById, findAll, delete, etc.
 */
@Repository // Indique que cette interface est un repository Spring (accès aux données)
public interface EleveRepository extends JpaRepository<Eleve, Long> {
    // Cette interface est volontairement vide
    // Spring Data JPA fournit automatiquement les implémentations des méthodes CRUD :
    // - save(Eleve entity) : sauvegarde un élève
    // - findById(Integer id) : trouve un élève par son ID
    // - findAll() : retourne tous les élèves
    // - deleteById(Integer id) : supprime un élève par son ID
    // - count() : compte le nombre d'élèves
    // - existsById(Integer id) : vérifie si un élève existe par son ID

    // Le générique <Eleve, Integer> signifie :
    // - Eleve : le type d'entité géré par ce repository
    // - Integer : le type de la clé primaire de l'entité Eleve (id_eleve)
}
