package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'agrégat {@link Classe} (ID de type {@link Long}).
 *
 * Fournit les opérations CRUD/pagination/tri standards via {@link JpaRepository}.
 */
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
}
