/*
 * Composant Modal réutilisable
 * Affiche une fenêtre modale avec titre, contenu et actions personnalisées
 * Gère la fermeture avec la touche Échap et en cliquant sur l'arrière-plan
 * Empêche le défilement du corps lorsque le modal est ouvert
 */
import React, { useEffect } from 'react'

export default function Modal({ open, onClose, title, children, actions }) {
  // Gestion des événements clavier et du style du corps
  useEffect(() => {
    function onKey(e) {
      // Fermeture du modal avec la touche Échap
      if (e.key === 'Escape') onClose?.()
    }
    if (open) {
      document.addEventListener('keydown', onKey)
      // Empêche le défilement du corps lorsque le modal est ouvert
      document.body.style.overflow = 'hidden'
    }
    return () => {
      document.removeEventListener('keydown', onKey)
      // Restaure le défilement du corps
      document.body.style.overflow = ''
    }
  }, [open, onClose])

  // Ne rend rien si le modal n'est pas ouvert
  if (!open) return null
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      {/* Arrière-plan semi-transparent */}
      <div className="absolute inset-0 bg-black/40" onClick={onClose} />
      {/* Contenu du modal */}
      <div className="relative z-10 w-full max-w-lg rounded-lg bg-white shadow-lg">
        {/* En-tête avec titre et bouton de fermeture */}
        <div className="flex items-center justify-between border-b px-5 py-3">
          <h3 className="text-base font-semibold">{title}</h3>
          <button onClick={onClose} className="text-gray-500 hover:text-gray-700">✕</button>
        </div>
        {/* Corps du modal */}
        <div className="px-5 py-4">{children}</div>
        {/* Pied du modal avec actions */}
        {actions && (
          <div className="flex items-center justify-end gap-2 border-t px-5 py-3">
            {actions}
          </div>
        )}
      </div>
    </div>
  )
}
