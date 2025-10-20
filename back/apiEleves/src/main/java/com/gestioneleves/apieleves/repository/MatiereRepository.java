package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Matiere;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatiereRepository extends CrudRepository<Matiere, Integer> {
}
