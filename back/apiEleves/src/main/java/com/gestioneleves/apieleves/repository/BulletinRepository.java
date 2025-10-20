package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Bulletin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository extends CrudRepository<Bulletin, Integer> {
}
