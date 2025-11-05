import React, { useEffect, useMemo, useState } from 'react'
import { api } from '../../lib/api'
import Spinner from '../../components/Spinner'

function isValidSchoolYear(value) {
  if (!/^\d{4}\/\d{4}$/.test(value)) return false
  const [a, b] = value.split('/').map(Number)
  return b === a + 1
}

export default function AdminStats() {
  const now = new Date()
  const y = now.getFullYear()
  const defaultYear = `${y}/${y + 1}`

  const [classes, setClasses] = useState([])
  const [matieres, setMatieres] = useState([])

  const [classeId, setClasseId] = useState('')
  const [matiereId, setMatiereId] = useState('')
  const [annee, setAnnee] = useState(defaultYear)
  const [trimestre, setTrimestre] = useState('T1')

  const [notes, setNotes] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadOptions() {
      try {
        const [cls, mats] = await Promise.all([
          api.listClasses({ page: 0, size: 200 }),
          api.listMatieres({ page: 0, size: 200 }),
        ])
        setClasses(cls?.content || [])
        setMatieres(mats?.content || [])
      } catch (_) {}
    }
    loadOptions()
  }, [])

  async function loadNotes() {
    setError('')
    if (!isValidSchoolYear(annee)) {
      setError("L'année doit être au format 2024/2025")
      return
    }
    setLoading(true)
    try {
      const params = { annee, trimestre, page: 0, size: 5000 }
      if (classeId) params.classeId = classeId
      if (matiereId) params.matiereId = matiereId
      const res = await api.listNotes(params)
      const items = Array.isArray(res) ? res : (res?.content || [])
      setNotes(items)
    } catch (e) {
      setError(e?.message || 'Erreur de chargement des notes')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadNotes()
  }, [classeId, matiereId, annee, trimestre])

  const avg = (arr) => {
    const vals = arr.map(n => Number(n.note ?? n.valeur)).filter(v => Number.isFinite(v))
    if (vals.length === 0) return null
    const sum = vals.reduce((a, b) => a + b, 0)
    return sum / vals.length
  }

  const kpiClasse = useMemo(() => {
    if (!notes.length) return null
    if (classeId) {
      return avg(notes)
    }
    const byClasse = {}
    for (const n of notes) {
      const key = n?.classe?.idClasse ?? n?.classeId ?? 'N/A'
      if (!byClasse[key]) byClasse[key] = []
      byClasse[key].push(n)
    }
    const avgs = Object.values(byClasse).map(list => avg(list)).filter(v => v != null)
    if (!avgs.length) return null
    return avgs.reduce((a, b) => a + b, 0) / avgs.length
  }, [notes, classeId])

  const kpiMatiere = useMemo(() => {
    if (!notes.length) return null
    if (matiereId) {
      return avg(notes)
    }
    const byMat = {}
    for (const n of notes) {
      const key = n?.matiere?.idMatiere ?? n?.matiereId ?? 'N/A'
      if (!byMat[key]) byMat[key] = []
      byMat[key].push(n)
    }
    const avgs = Object.values(byMat).map(list => avg(list)).filter(v => v != null)
    if (!avgs.length) return null
    return avgs.reduce((a, b) => a + b, 0) / avgs.length
  }, [notes, matiereId])

  return (
    <div className="space-y-4">
      <header className="flex flex-wrap items-end justify-between gap-3">
        <div>
          <h1 className="text-lg font-semibold">Statistiques (MVP)</h1>
          <p className="text-sm text-gray-600">Moyennes calculées côté front à partir des notes filtrées.</p>
        </div>
        <div className="flex flex-wrap items-center gap-2">
          <select className="input" value={classeId} onChange={(e) => setClasseId(e.target.value)}>
            <option value="">Toutes les classes</option>
            {classes.map(c => (
              <option key={c.idClasse} value={c.idClasse}>
                {(c.nomClasse ?? c.nom) + ((c.niveauClasse || c.niveau) ? ` (${c.niveauClasse ?? c.niveau})` : '')}
              </option>
            ))}
          </select>
          <select className="input" value={matiereId} onChange={(e) => setMatiereId(e.target.value)}>
            <option value="">Toutes les matières</option>
            {matieres.map(m => (
              <option key={m.idMatiere} value={m.idMatiere}>{m.nom}</option>
            ))}
          </select>
          <input className="input w-40" placeholder="Année (2024/2025)" value={annee} onChange={(e) => setAnnee(e.target.value)} />
          <select className="input" value={trimestre} onChange={(e) => setTrimestre(e.target.value)}>
            <option value="T1">T1</option>
            <option value="T2">T2</option>
            <option value="T3">T3</option>
          </select>
        </div>
      </header>

      {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}
      {loading && <Spinner />}

      {!loading && !error && (
        <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
          <KpiCard title={classeId ? 'Moyenne de la classe sélectionnée' : 'Moyenne globale par classe'} value={kpiClasse} />
          <KpiCard title={matiereId ? 'Moyenne de la matière sélectionnée' : 'Moyenne globale par matière'} value={kpiMatiere} />
        </div>
      )}

      <div className="text-xs text-gray-500">Remarque: pour des données volumineuses, préférez des endpoints d’agrégats dédiés côté backend.</div>
    </div>
  )
}

function KpiCard({ title, value }) {
  return (
    <div className="card p-4">
      <div className="text-sm text-gray-600">{title}</div>
      <div className="mt-1 text-2xl font-semibold">
        {value == null ? '—' : value.toFixed(2)}
      </div>
    </div>
  )
}
