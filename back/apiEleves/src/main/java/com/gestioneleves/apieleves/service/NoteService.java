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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    // Variante contrôleur-friendly: le service accepte la request et renvoie le DTO
    public NoteDTO createNote(NoteCreateRequest request){
        Note toSave = NoteMapper.fromCreate(request);
        Note saved = createNote(toSave);
        return NoteMapper.toDto(saved);
    }

    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public Note createNote (Note note){
        validateNote(note);
        return noteRepository.save(note);
    }

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    public NoteDTO editNote(Long id, NoteUpdateRequest request){
        Note current = getNoteById(id);
        Note updated = NoteMapper.applyUpdate(current, request);
        Note saved = editNote(id, updated);
        return NoteMapper.toDto(saved);
    }

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

    public void deleteNote(Long id){
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note introuvable: " + id);
        }
        noteRepository.deleteById(id);
    }

    public Note getNoteById(Long id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note introuvable: " + id));
    }

    private void validateNote(Note n) {
        if (n.getCoefNote() <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être > 0");
        }
        if (n.getValeurNote() < 0 || n.getValeurNote() > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20");
        }
    }
}
