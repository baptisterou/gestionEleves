package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Eleve;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleveRepository extends CrudRepository<Eleve, Integer> {
}
