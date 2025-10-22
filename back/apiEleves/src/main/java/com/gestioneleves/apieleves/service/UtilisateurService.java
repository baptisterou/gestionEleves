package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.UtilisateurDto;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository UtilisateurRepository;

        public List<Utilisateur> getAllUtilisateurs(){
            return UtilisateurRepository.findAll();
        }

        public Utilisateur ajouterUtilisateur(Utilisateur utilisateur){
            return UtilisateurRepository.save(utilisateur);
        }

        public void modifierUtilisateur(UtilisateurDto dto){

        }

        public void supprimerUtilisateur(Long id){

        }

        public UtilisateurDto getUtilisateurById(Long id){
            return null;
        }
}
