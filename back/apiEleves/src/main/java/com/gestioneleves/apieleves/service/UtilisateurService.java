package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.UtilisateurDto;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

        public void ajouterUtilisateur(UtilisateurDto dto, String password){
            System.out.println("ajouter un utilisateur");
        }

        public void modifierUtilisateur(UtilisateurDto dto){

        }

        public void supprimerUtilisateur(Long id){

        }

        public UtilisateurDto getUtilisateurById(Long id){
            return null;
        }
}
