package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'agrégat {@link Matiere} (ID de type {@link Long}).
 *
 * Fournit les opérations CRUD/pagination/tri standards via {@link JpaRepository}.
 * Les requêtes dérivées peuvent être ajoutées au besoin (findBy..., existsBy...).
 */
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
}
