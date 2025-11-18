import { useState, useEffect, useCallback } from 'react';
import { api } from '../lib/api';

/*
 * Hook personnalisé pour gérer les compteurs d'élèves, classes et matières
 * Permet un affichage en temps réel du nombre d'éléments dans chaque catégorie
 * Se met à jour automatiquement lorsque des événements sont émis
 */
export function useCounters() {
  // États pour stocker les compteurs
  const [countEleves, setCountEleves] = useState(0);
  const [countClasses, setCountClasses] = useState(0);
  const [countMatieres, setCountMatieres] = useState(0);
  // Trigger pour forcer la mise à jour des compteurs
  const [updateTrigger, setUpdateTrigger] = useState(0);

  // Fonction pour charger le nombre d'élèves
  const loadCountEleves = useCallback(async () => {
    try {
      // On ne demande qu'un seul élément pour obtenir le total
      const res = await api.listEleves({ page: 0, size: 1 });
      setCountEleves(res.totalElements);
    } catch (e) {
      console.error('Erreur lors du chargement des élèves', e);
      setCountEleves(0);
    }
  }, []);

  // Fonction pour charger le nombre de classes
  const loadCountClasses = useCallback(async () => {
    try {
      // On ne demande qu'un seul élément pour obtenir le total
      const res = await api.listClasses({ page: 0, size: 1 });
      setCountClasses(res.totalElements);
    } catch (e) {
      console.error('Erreur lors du chargement des classes', e);
      setCountClasses(0);
    }
  }, []);

  // Fonction pour charger le nombre de matières
  const loadCountMatieres = useCallback(async () => {
    try {
      // On ne demande qu'un seul élément pour obtenir le total
      const res = await api.listMatieres({ page: 0, size: 1 });
      setCountMatieres(res.totalElements);
    } catch (e) {
      console.error('Erreur lors du chargement des matières', e);
      setCountMatieres(0);
    }
  }, []);

  // Effet pour charger les compteurs au montage et lors des mises à jour
  useEffect(() => {
    loadCountEleves();
    loadCountClasses();
    loadCountMatieres();
  }, [updateTrigger, loadCountEleves, loadCountClasses, loadCountMatieres]);

  // Effet pour écouter les événements de mise à jour
  useEffect(() => {
    // Gestionnaires d'événements qui déclenchent la mise à jour
    const handleElevesUpdate = () => setUpdateTrigger(prev => prev + 1);
    const handleClassesUpdate = () => setUpdateTrigger(prev => prev + 1);
    const handleMatieresUpdate = () => setUpdateTrigger(prev => prev + 1);

    // Ajout des listeners d'événements
    window.addEventListener('eleves-updated', handleElevesUpdate);
    window.addEventListener('classes-updated', handleClassesUpdate);
    window.addEventListener('matieres-updated', handleMatieresUpdate);

    // Nettoyage des listeners lors du démontage
    return () => {
      window.removeEventListener('eleves-updated', handleElevesUpdate);
      window.removeEventListener('classes-updated', handleClassesUpdate);
      window.removeEventListener('matieres-updated', handleMatieresUpdate);
    };
  }, []);

  // Retour des compteurs et des fonctions de chargement
  return {
    countEleves,
    countClasses,
    countMatieres,
    loadCountEleves,
    loadCountClasses,
    loadCountMatieres
  };
}
