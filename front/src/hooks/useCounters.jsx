import { useState, useEffect } from 'react';
import { api } from '../lib/api';

export function useCounters() {
  const [countEleves, setCountEleves] = useState(0);
  const [countClasses, setCountClasses] = useState(0);
  const [countMatieres, setCountMatieres] = useState(0);

  const loadCountEleves = async () => {
    try {
      const res = await api.listEleves({ page: 0, size: 1 });
      setCountEleves(res.totalElements);
    } catch (e) {
      console.error('Erreur lors du chargement des élèves', e);
      setCountEleves(0);
    }
  };

  const loadCountClasses = async () => {
    try {
      const res = await api.listClasses({ page: 0, size: 1 });
      setCountClasses(res.totalElements);
    } catch (e) {
      console.error('Erreur lors du chargement des classes', e);
      setCountClasses(0);
    }
  };

  const loadCountMatieres = async () => {
    try {
      const res = await api.listMatieres({ page: 0, size: 1 });
      setCountMatieres(res.totalElements);
    } catch (e) {
      console.error('Erreur lors du chargement des matières', e);
      setCountMatieres(0);
    }
  };

  useEffect(() => {
    loadCountEleves();
    loadCountClasses();
    loadCountMatieres();

    // Écouter les événements de mise à jour
    const handleElevesUpdate = () => loadCountEleves();
    const handleClassesUpdate = () => loadCountClasses();
    const handleMatieresUpdate = () => loadCountMatieres();

    window.addEventListener('eleves-updated', handleElevesUpdate);
    window.addEventListener('classes-updated', handleClassesUpdate);
    window.addEventListener('matieres-updated', handleMatieresUpdate);

    return () => {
      window.removeEventListener('eleves-updated', handleElevesUpdate);
      window.removeEventListener('classes-updated', handleClassesUpdate);
      window.removeEventListener('matieres-updated', handleMatieresUpdate);
    };
  }, []);

  return {
    countEleves,
    countClasses,
    countMatieres,
    loadCountEleves,
    loadCountClasses,
    loadCountMatieres
  };
}
