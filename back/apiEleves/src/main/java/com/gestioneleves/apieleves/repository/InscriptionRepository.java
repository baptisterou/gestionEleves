package com.gestioneleves.apieleves.repository;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Inscription;
import com.gestioneleves.apieleves.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    //Trouver toutes les inscriptions d'un élève
    List<Inscription> findByEleve(Eleve eleve);

    //Trouver toutes les inscriptions faites par un administrateur
    List<Inscription> findByUtilisateur(Utilisateur utilisateur);

}
