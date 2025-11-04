import React from 'react'

export default function ConfirmDialog({ open, title = 'Confirmer', message, confirmLabel = 'Confirmer', cancelLabel = 'Annuler', onConfirm, onCancel }) {
  if (!open) return null
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <div className="absolute inset-0 bg-black/40" onClick={onCancel} />
      <div className="relative z-10 w-full max-w-md rounded-lg bg-white shadow-lg">
        <div className="border-b px-5 py-3">
          <h3 className="text-base font-semibold">{title}</h3>
        </div>
        <div className="px-5 py-4 text-sm text-gray-700">
          {message}
        </div>
        <div className="flex items-center justify-end gap-2 border-t px-5 py-3">
          <button className="btn btn-outline" onClick={onCancel}>{cancelLabel}</button>
          <button className="btn btn-primary" onClick={onConfirm}>{confirmLabel}</button>
        </div>
      </div>
    </div>
  )
}
