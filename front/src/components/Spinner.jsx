import React from 'react'

export default function Spinner({ label = 'Chargement…', className = '' }) {
  return (
    <div className={`flex items-center gap-2 text-sm text-gray-500 ${className}`}>
      <svg className="h-4 w-4 animate-spin text-gray-400" viewBox="0 0 24 24">
        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z" />
      </svg>
      <span>{label}</span>
    </div>
  )
}
