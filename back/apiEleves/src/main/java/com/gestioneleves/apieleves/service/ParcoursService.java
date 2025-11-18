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

/**
 * Service métier pour la gestion des {@code Parcours}.
 *
 * Responsabilités:
 * - Lister, créer, modifier et supprimer des parcours
 * - Gérer les associations avec {@link Eleve} et {@link Classe}
 * - Exposer des variantes orientées contrôleur (entrée requête/DTO, sortie DTO)
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link org.springframework.transaction.annotation.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si le parcours/élève/classe est introuvable
 */
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

    /**
     * Récupère tous les parcours (non paginé).
     */
    public List<Parcours> getAllParcours(){ return parcoursRepository.findAll(); }

    /**
     * Crée un parcours à partir d'une requête de création et retourne un DTO.
     */
    public ParcoursDTO createParcours(ParcoursCreateRequest request){
        Parcours toSave = ParcoursMapper.fromCreate(request);
        Parcours saved = createParcours(toSave);
        return ParcoursMapper.toDto(saved);
    }

    /**
     * Persiste une entité parcours.
     */
    public Parcours createParcours(Parcours parcours){ return parcoursRepository.save(parcours); }

    /**
     * Met à jour partiellement un parcours et retourne un DTO.
     */
    public ParcoursDTO editParcours(Long id, ParcoursDTO request) {
        Parcours current = getParcoursById(id);
        Parcours updatedEntity = ParcoursMapper.applyUpdate(current, request);
        Parcours saved = editParcours(id, updatedEntity);
        return ParcoursMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle à un parcours existant.
     *
     * @throws EntityNotFoundException si le parcours/élève/classe n'existe pas
     */
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

    /**
     * Supprime un parcours par identifiant.
     */
    public void deleteParcours(Long id){
        if (!parcoursRepository.existsById(id)) {
            throw new EntityNotFoundException("Parcours introuvable:" + id);
        }
        parcoursRepository.deleteById(id);
    }

    /**
     * Récupère un parcours par identifiant.
     */
    public Parcours getParcoursById(Long id){
        return parcoursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcours introuvable: " + id));
    }
}
