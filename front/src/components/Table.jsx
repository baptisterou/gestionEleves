/*
 * Composant Tableau générique réutilisable
 * Affiche des données sous forme de tableau avec des colonnes configurables
 * Gère l'affichage des données vides et le rendu personnalisé des cellules
 */
import React from 'react'

export default function Table({ columns = [], data = [], emptyLabel = 'Aucune donnée', keyField = 'id' }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        {/* En-tête du tableau */}
        <thead className="bg-gray-50">
          <tr>
            {columns.map((col) => (
              <th key={col.key || col.accessor}
                  className="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">
                {col.header}
              </th>
            ))}
          </tr>
        </thead>
        {/* Corps du tableau */}
        <tbody className="divide-y divide-gray-100 bg-white">
          {/* Message affiché si aucune donnée */}
          {(!data || data.length === 0) && (
            <tr>
              <td colSpan={columns.length} className="px-4 py-6 text-center text-sm text-gray-500">{emptyLabel}</td>
            </tr>
          )}
          {/* Lignes de données */}
          {data && data.map((row, idx) => (
            <tr key={row[keyField] ?? idx} className="hover:bg-gray-50">
              {columns.map((col) => {
                // Extraction de la valeur selon le type d'accesseur
                let value
                if (typeof col.accessor === 'function') {
                  value = col.accessor(row)
                } else {
                  const keyName = col.accessor ?? col.key
                  value = keyName ? row[keyName] : undefined
                }
                return (
                  <td key={(col.key || col.accessor || 'col') + '_' + (row[keyField] ?? idx)} className="px-4 py-3 text-sm text-gray-800">
                    {/* Utilisation d'un rendu personnalisé si défini */}
                    {col.render ? col.render(value, row) : String(value ?? '')}
                  </td>
                )
              })}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
