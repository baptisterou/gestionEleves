package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
}
