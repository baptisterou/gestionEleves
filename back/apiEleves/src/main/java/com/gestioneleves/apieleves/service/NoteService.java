package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.NoteCreateRequest;
import com.gestioneleves.apieleves.dto.NoteDTO;
import com.gestioneleves.apieleves.dto.NoteUpdateRequest;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Note;
import com.gestioneleves.apieleves.mapper.NoteMapper;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import com.gestioneleves.apieleves.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la gestion des {@code Note}.
 *
 * Responsabilités:
 * - Créer, lire, mettre à jour et supprimer des notes
 * - Valider les contraintes métier (bornes de valeur, coefficient)
 * - Gérer les associations avec {@link Bulletin}, {@link Eleve} et {@link Matiere}
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link jakarta.transaction.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si une ressource liée est introuvable
 * - {@link IllegalArgumentException} pour les validations métier
 */
@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    private BulletinRepository bulletinRepository;
    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private MatiereRepository matiereRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Crée une note à partir d'une requête de création et retourne un DTO.
     *
     * @param request données de création (champs requis)
     * @return la note créée sous forme de {@link NoteDTO}
     */
    public NoteDTO createNote(NoteCreateRequest request){
        Note toSave = NoteMapper.fromCreate(request);
        Note saved = createNote(toSave);
        return NoteMapper.toDto(saved);
    }

    /**
     * Récupère toutes les notes (non paginé).
     *
     * @return liste de notes
     */
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    /**
     * Persiste une nouvelle note après validation métier.
     *
     * @param note entité à sauvegarder
     * @return entité sauvegardée
     */
    public Note createNote (Note note){
        validateNote(note);
        return noteRepository.save(note);
    }

    /**
     * Met à jour partiellement une note et retourne un DTO.
     *
     * @param id identifiant de la note à modifier
     * @param request champs à mettre à jour
     * @return la note mise à jour sous forme de {@link NoteDTO}
     */
    public NoteDTO editNote(Long id, NoteUpdateRequest request){
        Note current = getNoteById(id);
        Note updated = NoteMapper.applyUpdate(current, request);
        Note saved = editNote(id, updated);
        return NoteMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle à une note existante et la persiste.
     *
     * @param id identifiant
     * @param note valeurs à appliquer (champs non nuls et valides)
     * @return entité sauvegardée
     * @throws EntityNotFoundException si la note ou une ressource liée n'existe pas
     */
    public Note editNote(Long id, Note note){
        // Récupération ou exception si non trouvé
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note introuvable : " + id));

        // Mise à jour des champs simples
        if (note.getDateNote() != null) {
            existing.setDateNote(note.getDateNote());
        }
        if (note.getCoefNote() > 0) {
            existing.setCoefNote(note.getCoefNote());
        }
        if (note.getValeurNote() >=  0 && note.getValeurNote() <= 20) {
            existing.setValeurNote(note.getValeurNote());
        }

        // Mise à jour de l'objet lié
        if (note.getBulletin() != null && note.getBulletin().getIdBulletin() != null) {
            Bulletin bulletin = bulletinRepository.findById(note.getBulletin().getIdBulletin())
                    .orElseThrow(() -> new EntityNotFoundException("Bulletin introuvable : " + note.getBulletin().getIdBulletin()));
            existing.setBulletin(bulletin);
        }

        if (note.getEleve() != null && note.getEleve().getIdEleve() != null) {
            Eleve eleve = eleveRepository.findById(note.getEleve().getIdEleve())
                    .orElseThrow(() -> new EntityNotFoundException("Eleve introuvable : " + note.getEleve().getIdEleve()));
            existing.setEleve(eleve);
        }

        if (note.getMatiere() != null && note.getMatiere().getIdMatiere() != null) {
            Matiere matiere = matiereRepository.findById(note.getMatiere().getIdMatiere())
                    .orElseThrow(() -> new EntityNotFoundException("Matière  introuvable : " + note.getMatiere().getIdMatiere()));
            existing.setMatiere(matiere);
        }

        // Sauvegarde et retour
        return noteRepository.save(existing);
    }

    /**
     * Supprime une note par identifiant.
     *
     * @param id identifiant de la note
     * @throws EntityNotFoundException si la note n'existe pas
     */
    public void deleteNote(Long id){
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note introuvable: " + id);
        }
        noteRepository.deleteById(id);
    }

    /**
     * Récupère une note par identifiant.
     *
     * @param id identifiant recherché
     * @return entité trouvée
     * @throws EntityNotFoundException si aucune note ne correspond
     */
    public Note getNoteById(Long id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note introuvable: " + id));
    }

    /**
     * Valide les règles métier d'une note.
     *
     * @param n note à valider
     * @throws IllegalArgumentException si une règle est violée
     */
    private void validateNote(Note n) {
        if (n.getCoefNote() <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être > 0");
        }
        if (n.getValeurNote() < 0 || n.getValeurNote() > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20");
        }
    }
}
