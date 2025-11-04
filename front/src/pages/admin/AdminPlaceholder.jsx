import React from 'react'

export default function AdminPlaceholder({ title }) {
  return (
    <div className="space-y-3">
      <h1 className="text-lg font-semibold">{title}</h1>
      <div className="rounded-md border border-dashed border-gray-300 p-6 text-sm text-gray-600">
        Cette section sera bientôt disponible. Les composants de base (Table, Pagination, etc.) sont prêts.
      </div>
    </div>
  )
}
