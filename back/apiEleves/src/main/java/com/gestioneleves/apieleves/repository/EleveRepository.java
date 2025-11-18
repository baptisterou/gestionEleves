package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'agrégat {@link Eleve} (ID de type {@link Long}).
 *
 * Fournit les opérations CRUD/pagination/tri standards via {@link JpaRepository}.
 */
@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {
}
