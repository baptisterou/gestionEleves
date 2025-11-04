import React, { useEffect, useMemo, useState } from 'react'
import { api } from '../../lib/api'
import Spinner from '../../components/Spinner'
import { useDebounce } from '../../lib/useDebounce'
import { useToast } from '../../lib/useToast'

function isValidSchoolYear(value) {
  if (!/^\d{4}\/\d{4}$/.test(value)) return false
  const [a, b] = value.split('/').map(Number)
  return b === a + 1
}

export default function GuardianNotes() {
  const { show } = useToast()
  const current = new Date()
  const y = current.getFullYear()
  const defaultYear = `${y}/${y + 1}`

  const [query, setQuery] = useState('')
  const debounced = useDebounce(query, 300)
  const [options, setOptions] = useState([])
  const [loadingOptions, setLoadingOptions] = useState(false)

  const [eleveId, setEleveId] = useState('')
  const [annee, setAnnee] = useState(defaultYear)
  const [trimestre, setTrimestre] = useState('T1')

  const [notes, setNotes] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    let active = true
    async function load() {
      setLoadingOptions(true)
      try {
        const res = await api.listEleves({ page: 0, size: 10, ...(debounced ? { q: debounced } : {}) })
        if (active) setOptions(res?.content || [])
      } catch (e) {
      } finally {
        if (active) setLoadingOptions(false)
      }
    }
    load()
    return () => { active = false }
  }, [debounced])

  async function loadNotes() {
    setError('')
    if (!eleveId) {
      setError("Veuillez sélectionner un élève")
      return
    }
    if (!isValidSchoolYear(annee)) {
      setError("L'année doit être au format 2024/2025 (année droite = année gauche + 1)")
      return
    }
    setLoading(true)
    try {
      const res = await api.listNotes({ eleveId, annee, trimestre, page: 0, size: 1000 })
      const items = Array.isArray(res) ? res : (res?.content || [])
      setNotes(items)
    } catch (e) {
      setError(e?.message || 'Erreur de chargement des notes')
      show(e?.message || 'Erreur de chargement des notes', { type: 'error' })
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    if (eleveId) loadNotes()
  }, [eleveId, annee, trimestre])

  const grouped = useMemo(() => {
    const byMat = {}
    for (const n of notes) {
      const mat = n?.matiere?.nom || n?.matiereNom || `Matière #${n?.matiereId ?? 'N/A'}`
      if (!byMat[mat]) byMat[mat] = []
      byMat[mat].push(n)
    }
    return byMat
  }, [notes])

  return (
    <div className="space-y-6">
      <h1 className="text-xl font-semibold">Notes de l'élève</h1>

      <div className="card p-4 space-y-3">
        <div className="grid grid-cols-1 gap-3 md:grid-cols-2 lg:grid-cols-4">
          <div>
            <label className="label">Rechercher un élève</label>
            <div className="relative">
              <input
                className="input"
                placeholder="Nom ou prénom"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
              />
              {loadingOptions && <div className="absolute right-2 top-2"><Spinner label="" /></div>}
            </div>
            <div className="mt-2 max-h-48 overflow-auto rounded-md border bg-white">
              {(options || []).map(el => (
                <button
                  key={el.idEleve}
                  type="button"
                  className={`block w-full px-3 py-2 text-left text-sm hover:bg-gray-50 ${String(eleveId) === String(el.idEleve) ? 'bg-gray-100' : ''}`}
                  onClick={() => { setEleveId(el.idEleve); setQuery(`${el.prenom} ${el.nom}`) }}
                >
                  {el.prenom} {el.nom} (#{el.idEleve})
                </button>
              ))}
              {(!options || options.length === 0) && (
                <div className="px-3 py-2 text-sm text-gray-500">Aucun résultat</div>
              )}
            </div>
          </div>

          <div>
            <label className="label">Année scolaire</label>
            <input
              className="input"
              placeholder="2024/2025"
              value={annee}
              onChange={(e) => setAnnee(e.target.value)}
            />
            <p className="mt-1 text-xs text-gray-500">Format: YYYY/YYYY+1</p>
          </div>

          <div>
            <label className="label">Trimestre</label>
            <select className="input" value={trimestre} onChange={(e) => setTrimestre(e.target.value)}>
              <option value="T1">T1</option>
              <option value="T2">T2</option>
              <option value="T3">T3</option>
            </select>
          </div>

          <div className="flex items-end">
            <button className="btn btn-primary" onClick={loadNotes}>Charger les notes</button>
          </div>
        </div>
      </div>

      {error && (
        <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>
      )}
      {loading && <Spinner />}

      {!loading && !error && eleveId && (
        <div className="space-y-4">
          {Object.keys(grouped).length === 0 && (
            <div className="rounded-md border border-gray-200 bg-white p-4 text-sm text-gray-600">Aucune note pour ces filtres.</div>
          )}
          {Object.entries(grouped).map(([matiere, list]) => (
            <div key={matiere} className="card p-4">
              <div className="mb-2 text-sm font-semibold">{matiere}</div>
              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                  <thead className="bg-gray-50">
                    <tr>
                      <th className="px-3 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Note</th>
                      <th className="px-3 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Coefficient</th>
                      <th className="px-3 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Appréciation</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-100 bg-white">
                    {list.map((n, i) => (
                      <tr key={n.idNote ?? i}>
                        <td className="px-3 py-2 text-sm">{n.note ?? n.valeur ?? '-'}</td>
                        <td className="px-3 py-2 text-sm">{n.coefficient ?? 1}</td>
                        <td className="px-3 py-2 text-sm">{n.appreciation ?? ''}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          ))}

          <div className="card p-4">
            <div className="flex flex-wrap items-end gap-3">
              <div className="text-sm text-gray-700">Exporter le bulletin de l'élève sélectionné</div>
              <button
                className="btn btn-primary"
                onClick={async () => {
                  if (!eleveId) return
                  if (!isValidSchoolYear(annee)) {
                    show("Année invalide (ex: 2024/2025)", { type: 'error' })
                    return
                  }
                  try {
                    const { blob, contentDisposition } = await api.exportBulletin({ eleveId, trimestre, annee })
                    const { filenameFromContentDisposition, saveBlob } = await import('../../lib/download')
                    const filename = filenameFromContentDisposition(contentDisposition, `bulletin_${eleveId}_${trimestre}_${annee}.pdf`)
                    saveBlob(blob, filename)
                    show('Export en cours…', { type: 'success' })
                  } catch (e) {
                    show(e?.message || "Erreur lors de l'export", { type: 'error' })
                  }
                }}
              >
                Exporter le bulletin ({trimestre})
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
