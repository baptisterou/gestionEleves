import React from 'react'

export default function Table({ columns = [], data = [], emptyLabel = 'Aucune donnée', keyField = 'id' }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
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
        <tbody className="divide-y divide-gray-100 bg-white">
          {(!data || data.length === 0) && (
            <tr>
              <td colSpan={columns.length} className="px-4 py-6 text-center text-sm text-gray-500">{emptyLabel}</td>
            </tr>
          )}
          {data && data.map((row, idx) => (
            <tr key={row[keyField] ?? idx} className="hover:bg-gray-50">
              {columns.map((col) => {
                let value
                if (typeof col.accessor === 'function') {
                  value = col.accessor(row)
                } else {
                  const keyName = col.accessor ?? col.key
                  value = keyName ? row[keyName] : undefined
                }
                return (
                  <td key={(col.key || col.accessor || 'col') + '_' + (row[keyField] ?? idx)} className="px-4 py-3 text-sm text-gray-800">
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
