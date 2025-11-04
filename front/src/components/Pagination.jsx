import React from 'react'

export default function Pagination({ page = 0, size = 20, totalElements = 0, onPageChange }) {
  const totalPages = Math.max(1, Math.ceil(totalElements / size))
  const canPrev = page > 0
  const canNext = page + 1 < totalPages

  return (
    <div className="mt-4 flex items-center justify-between">
      <div className="text-sm text-gray-600">
        Page {page + 1} / {totalPages} • {totalElements} éléments
      </div>
      <div className="flex items-center gap-2">
        <button className="btn btn-outline" disabled={!canPrev} onClick={() => onPageChange?.(page - 1)}>
          Précédent
        </button>
        <button className="btn btn-outline" disabled={!canNext} onClick={() => onPageChange?.(page + 1)}>
          Suivant
        </button>
      </div>
    </div>
  )
}
