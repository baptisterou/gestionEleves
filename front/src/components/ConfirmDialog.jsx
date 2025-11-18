/*
 * Composant de dialogue de confirmation
 * Affiche un message avec des boutons pour confirmer ou annuler une action
 * Utilisé pour les opérations de suppression ou autres actions critiques
 */
import React from 'react'

export default function ConfirmDialog({ open, title = 'Confirmer', message, confirmLabel = 'Confirmer', cancelLabel = 'Annuler', onConfirm, onCancel }) {
  // Ne rend rien si la boîte de dialogue n'est pas ouverte
  if (!open) return null
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      {/* Arrière-plan semi-transparent */}
      <div className="absolute inset-0 bg-black/40" onClick={onCancel} />
      {/* Contenu de la boîte de dialogue */}
      <div className="relative z-10 w-full max-w-md rounded-lg bg-white shadow-lg">
        {/* En-tête avec le titre */}
        <div className="border-b px-5 py-3">
          <h3 className="text-base font-semibold">{title}</h3>
        </div>
        {/* Corps avec le message de confirmation */}
        <div className="px-5 py-4 text-sm text-gray-700">
          {message}
        </div>
        {/* Pied avec les boutons d'action */}
        <div className="flex items-center justify-end gap-2 border-t px-5 py-3">
          <button className="btn btn-outline" onClick={onCancel}>{cancelLabel}</button>
          <button className="btn btn-primary" onClick={onConfirm}>{confirmLabel}</button>
        </div>
      </div>
    </div>
  )
}
