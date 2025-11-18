package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.ClasseCreateRequest;
import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.dto.ClasseUpdateRequest;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.ClasseMapper;
import com.gestioneleves.apieleves.repository.ClasseRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la gestion des {@code Classe}.
 *
 * Responsabilités:
 * - Créer, lister, mettre à jour et supprimer des classes
 * - Appliquer des mises à jour partielles
 * - Gérer la pagination et le mapping DTO lorsque nécessaire
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link jakarta.transaction.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si la classe n'existe pas
 */
@Service
@Transactional
public class ClasseService {

    private final ClasseRepository classeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    /**
     * Crée une classe à partir d'une requête de création et retourne un DTO.
     *
     * @param request données de création
     * @return la classe créée sous forme de DTO
     */
    public ClasseDTO createClasse(ClasseCreateRequest request) {
        Classe toSave = ClasseMapper.fromCreate(request);
        Classe saved = createClasse(toSave);
        return ClasseMapper.toDto(saved);
    }

    /**
     * Persiste une nouvelle entité classe.
     */
    public Classe createClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    /**
     * Retourne toutes les classes (non paginé).
     */
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    /**
     * Retourne les classes paginées.
     */
    public Page<Classe> getAllClasses(Pageable pageable) {
        return classeRepository.findAll(pageable);
    }

    /**
     * Met à jour partiellement une classe et retourne un DTO.
     */
    public ClasseDTO editClasse(Long id, ClasseUpdateRequest request) {
        Classe current = getClasseById(id);
        Classe updated = ClasseMapper.applyUpdate(current, request);
        Classe saved = editClasse(id, updated);
        return ClasseMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle à une classe existante.
     *
     * @throws EntityNotFoundException si la classe n'existe pas
     */
    public Classe editClasse(Long id, Classe classe){
        // Récupération ou exception si non trouvé
        Classe existing = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable : " + id));

        // Mise à jour des champs simples
        if (classe.getNomClasse() != null) {
            existing.setNomClasse(classe.getNomClasse());
        }
        if (classe.getNiveauClasse() != null) {
            existing.setNiveauClasse(classe.getNiveauClasse());
        }
        if (classe.getAnneeScolaire() != null) {
            existing.setAnneeScolaire(classe.getAnneeScolaire());
        }

        return classeRepository.save(existing);
    }

    /**
     * Récupère une classe par identifiant.
     */
    public Classe getClasseById(Long id){
        return classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable: " + id));
    }

    /**
     * Supprime une classe par identifiant.
     *
     * @throws EntityNotFoundException si la classe n'existe pas
     */
    public void deleteClasse (Long id_classe) {
        if (!classeRepository.existsById(id_classe)) {
            throw new EntityNotFoundException("Classe introuvable: " + id_classe);
        }
        classeRepository.deleteById(id_classe);
    }
}
