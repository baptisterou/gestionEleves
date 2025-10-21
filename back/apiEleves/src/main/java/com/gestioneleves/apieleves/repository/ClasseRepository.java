package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Classe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends CrudRepository<Classe, Long> {
}
