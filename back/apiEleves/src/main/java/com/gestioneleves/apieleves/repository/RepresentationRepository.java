package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Representation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentationRepository extends JpaRepository<Representation, Long> {
}
