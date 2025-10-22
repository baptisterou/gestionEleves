package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

        public List<Utilisateur> getAllUtilisateurs(){
            return UtilisateurRepository.findAll();
        }

        public Utilisateur ajouterUtilisateur(Utilisateur utilisateur){
            return utilisateurRepository.save(utilisateur);
        }

        public Utilisateur modifierUtilisateur(Utilisateur user){
            return utilisateurRepository.save(user);
        }

        public void supprimerUtilisateur(Long id){
            utilisateurRepository.deleteById(id);
        }

        public Utilisateur getUtilisateurById(Long id){
            return null;
        }
}
