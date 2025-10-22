package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Inscrire;
import com.gestioneleves.apieleves.entity.InscrireId;
import com.gestioneleves.apieleves.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscrireRepository extends JpaRepository<Inscrire, InscrireId> {

    //Trouver toutes les inscriptions d'un élève
    List<Inscrire> findByEleve(Eleve eleve);

    //Trouver toutes les inscriptions faites par un administrateur
    List<Inscrire> findByAdministrateur(Utilisateur administrateur);

}
