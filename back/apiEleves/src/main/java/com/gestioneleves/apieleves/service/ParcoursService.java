package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.ParcoursCreateRequest;
import com.gestioneleves.apieleves.dto.ParcoursDTO;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Parcours;
import com.gestioneleves.apieleves.mapper.ParcoursMapper;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.ParcoursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParcoursService {

    private final ParcoursRepository parcoursRepository;
    private final EleveRepository eleveRepository;
    private final ClasseRepository classeRepository;

    public ParcoursService(ParcoursRepository parcoursRepository, EleveRepository eleveRepository,  ClasseRepository classeRepository) {
        this.parcoursRepository = parcoursRepository;
        this.eleveRepository = eleveRepository;
        this.classeRepository = classeRepository;
    }

    public List<Parcours> getAllParcours(){ return parcoursRepository.findAll(); }

    // Variante contrôleur-friendly
    public ParcoursDTO createParcours(ParcoursCreateRequest request){
        Parcours toSave = ParcoursMapper.fromCreate(request);
        Parcours saved = createParcours(toSave);
        return ParcoursMapper.toDto(saved);
    }

    public Parcours createParcours(Parcours parcours){ return parcoursRepository.save(parcours); }

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    public ParcoursDTO editParcours(Long id, ParcoursDTO request) {
        Parcours current = getParcoursById(id);
        Parcours updatedEntity = ParcoursMapper.applyUpdate(current, request);
        Parcours saved = editParcours(id, updatedEntity);
        return ParcoursMapper.toDto(saved);
    }

    public Parcours editParcours(Long id, Parcours parcours){
        // Récupération ou exception si non trouvé
        Parcours existing = parcoursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcours introuvable : " + id));

        // Mise à jour de l'élève lié (si fourni)
        if (parcours.getEleve() != null && parcours.getEleve().getIdEleve() != null) {
            Eleve eleve = eleveRepository.findById(parcours.getEleve().getIdEleve())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Élève introuvable : " + parcours.getEleve().getIdEleve()));
            existing.setEleve(eleve);
        }

        // Mise à jour de la classe liée (si fourni)
        if (parcours.getClasse() != null && parcours.getClasse().getIdClasse() != null) {
            Classe classe = classeRepository.findById(parcours.getClasse().getIdClasse())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Classe introuvable : " + parcours.getClasse().getIdClasse()));
            existing.setClasse(classe);
        }

        //Sauvegarde et retour
        return parcoursRepository.save(existing); }

    public void deleteParcours(Long id){
        if (!parcoursRepository.existsById(id)) {
            throw new EntityNotFoundException("Parcours introuvable:" + id);
        }
        parcoursRepository.deleteById(id);
    }

    public Parcours getParcoursById(Long id){
        return parcoursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcours introuvable: " + id));
    }
}
