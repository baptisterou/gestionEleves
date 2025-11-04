import React, { useEffect, useMemo, useState } from 'react'
import { api } from '../../lib/api'
import { useToast } from '../../lib/useToast'

function currentSchoolYear() {
  const d = new Date()
  const y = d.getMonth() >= 7 ? d.getFullYear() : d.getFullYear() - 1
  return `${y}/${y + 1}`
}

function isValidSchoolYear(str) {
  if (!/^\d{4}\/\d{4}$/.test(str)) return false
  const [y1, y2] = str.split('/').map((n) => parseInt(n, 10))
  return y2 === y1 + 1
}

const TRIMESTRES = ['T1', 'T2', 'T3']

export default function TeacherNotes() {
  const { show } = useToast()


  const [classes, setClasses] = useState([])
  const [matieres, setMatieres] = useState([])
  const [classeId, setClasseId] = useState('')
  const [matiereId, setMatiereId] = useState('')
  const [trimestre, setTrimestre] = useState('T1')
  const [annee, setAnnee] = useState(currentSchoolYear())

  const [inscriptions, setInscriptions] = useState([])
  const [notes, setNotes] = useState([])

  const [loadingFilters, setLoadingFilters] = useState(true)
  const [loadingData, setLoadingData] = useState(false)
  const [error, setError] = useState('')

  const notesByEleve = useMemo(() => {
    const map = new Map()
    for (const n of notes || []) {

      const id = n.idNote ?? n.id
      map.set(n.eleveId ?? n.eleve?.idEleve ?? n.eleve?.id, {
        idNote: id,
        eleveId: n.eleveId ?? n.eleve?.idEleve ?? n.eleve?.id,
        note: typeof n.note === 'number' ? n.note : n.note != null ? Number(n.note) : null,
        coefficient: n.coefficient != null ? Number(n.coefficient) : 1,
        appreciation: n.appreciation ?? '',
      })
    }
    return map
  }, [notes])

  useEffect(() => {
    async function loadFilters() {
      setLoadingFilters(true)
      try {
        const [cls, mats] = await Promise.all([
          api.listClasses({ page: 0, size: 100 }),
          api.listMatieres({ page: 0, size: 100 }),
        ])
        setClasses(cls?.content || [])
        setMatieres(mats?.content || [])
        if ((cls?.content || []).length && !classeId) setClasseId(String(cls.content[0].idClasse))
        if ((mats?.content || []).length && !matiereId) setMatiereId(String(mats.content[0].idMatiere))
      } catch (e) {
        setError(e?.message || 'Erreur lors du chargement des filtres')
      } finally {
        setLoadingFilters(false)
      }
    }
    loadFilters()
  }, [])

  async function loadData() {
    if (!classeId || !matiereId || !trimestre || !annee) return
    if (!isValidSchoolYear(annee)) {
      setError("Année invalide: utilisez le format YYYY/YYYY+1 (ex: 2024/2025)")
      return
    }
    setLoadingData(true)
    setError('')
    try {
      const ins = await api.listInscriptions({ page: 0, size: 500, classeId, annee })
      setInscriptions(ins?.content || [])
      const nts = await api.listNotes({ page: 0, size: 1000, classeId, matiereId, trimestre, annee })
      setNotes(nts?.content || nts || [])
    } catch (e) {
      setError(e?.message || 'Erreur de chargement des données')
    } finally {
      setLoadingData(false)
    }
  }

  useEffect(() => {
    if (classeId && matiereId && trimestre && annee) {
      loadData()
    }
  }, [classeId, matiereId, trimestre, annee])

  function validateNoteValue(val) {
    if (val === '' || val === null || typeof val === 'undefined') return 'La note est requise'
    const num = Number(val)
    if (!Number.isFinite(num)) return 'La note doit être un nombre'
    if (num < 0 || num > 20) return 'La note doit être comprise entre 0 et 20'
    return ''
  }

  async function saveRow(eleve, inputs, onDone) {
    const err = validateNoteValue(inputs.note)
    if (err) {
      show(err, { type: 'error' })
      onDone?.(false)
      return
    }
    const payloadBase = {
      eleveId: eleve?.idEleve ?? eleve?.id,
      classeId: Number(classeId),
      matiereId: Number(matiereId),
      trimestre,
      annee,
      note: Number(inputs.note),
      coefficient: inputs.coefficient != null && inputs.coefficient !== '' ? Number(inputs.coefficient) : 1,
      appreciation: inputs.appreciation?.trim() || '',
    }

    try {
      const existing = notesByEleve.get(payloadBase.eleveId)
      if (existing?.idNote) {
        await api.updateNote(existing.idNote, payloadBase)
        show('Note mise à jour', { type: 'success' })
      } else {
        await api.createNote(payloadBase)
        show('Note enregistrée', { type: 'success' })
      }
      await loadData()
      onDone?.(true)
    } catch (e) {
      show(e?.message || 'Erreur lors de la sauvegarde', { type: 'error' })
      onDone?.(false)
    }
  }

  async function deleteRow(eleve) {
    try {
      const existing = notesByEleve.get(eleve?.idEleve ?? eleve?.id)
      if (!existing?.idNote) return
      await api.deleteNote(existing.idNote)
      show('Note supprimée', { type: 'success' })
      await loadData()
    } catch (e) {
      show(e?.message || 'Erreur lors de la suppression', { type: 'error' })
    }
  }

  const rows = useMemo(() => {
    return (inscriptions || []).map((ins) => {
      const eleve = ins.eleve || ins.eleveDTO || ins
      const eleveId = eleve?.idEleve ?? ins.eleveId ?? eleve?.id
      const base = {
        eleveId,
        nom: (eleve?.nom ?? ins.nom) || '',
        prenom: (eleve?.prenom ?? ins.prenom) || '',
      }
      const note = notesByEleve.get(eleveId)
      return {
        ...base,
        note: note?.note ?? '',
        coefficient: note?.coefficient ?? 1,
        appreciation: note?.appreciation ?? '',
      }
    })
  }, [inscriptions, notesByEleve])

  return (
    <div className="space-y-4">
      <header className="space-y-3">
        <h1 className="text-lg font-semibold">Saisie des notes</h1>
        <div className="card grid grid-cols-1 gap-3 p-4 sm:grid-cols-2 lg:grid-cols-4">
          <div>
            <label className="label">Classe</label>
            <select className="input" value={classeId} onChange={(e) => setClasseId(e.target.value)}>
              <option value="">Sélectionner…</option>
              {classes.map((c) => (
                <option key={c.idClasse} value={c.idClasse}>
                  {(c.nomClasse ?? c.nom) + ((c.niveauClasse || c.niveau) ? ` (${c.niveauClasse ?? c.niveau})` : '')}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label className="label">Matière</label>
            <select className="input" value={matiereId} onChange={(e) => setMatiereId(e.target.value)}>
              <option value="">Sélectionner…</option>
              {matieres.map((m) => (
                <option key={m.idMatiere} value={m.idMatiere}>{m.nom}</option>
              ))}
            </select>
          </div>
          <div>
            <label className="label">Trimestre</label>
            <select className="input" value={trimestre} onChange={(e) => setTrimestre(e.target.value)}>
              {TRIMESTRES.map((t) => <option key={t} value={t}>{t}</option>)}
            </select>
          </div>
          <div>
            <label className="label">Année scolaire</label>
            <input className="input" value={annee} onChange={(e) => setAnnee(e.target.value)} placeholder="2024/2025" />
          </div>
        </div>
      </header>

      {(loadingFilters || loadingData) && <div className="text-sm text-gray-500">Chargement…</div>}
      {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

      {!loadingFilters && !loadingData && classeId && matiereId && (
        <div className="card p-4">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-4 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Élève</th>
                  <th className="px-4 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Note (0–20)</th>
                  <th className="px-4 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Coefficient</th>
                  <th className="px-4 py-2 text-left text-xs font-semibold uppercase tracking-wide text-gray-500">Appréciation</th>
                  <th className="px-4 py-2"></th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-100 bg-white">
                {rows.length === 0 && (
                  <tr>
                    <td colSpan={5} className="px-4 py-6 text-center text-sm text-gray-500">Aucun élève inscrit pour cette classe/année.</td>
                  </tr>
                )}
                {rows.map((r) => (
                  <NoteRow
                    key={r.eleveId}
                    row={r}
                    onSave={(inputs, done) => saveRow({ idEleve: r.eleveId }, inputs, done)}
                    onDelete={() => deleteRow({ idEleve: r.eleveId })}
                  />
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  )
}

function NoteRow({ row, onSave, onDelete }) {
  const [note, setNote] = useState(row.note === '' || row.note == null ? '' : row.note)
  const [coefficient, setCoefficient] = useState(row.coefficient ?? 1)
  const [appreciation, setAppreciation] = useState(row.appreciation ?? '')
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    setNote(row.note === '' || row.note == null ? '' : row.note)
    setCoefficient(row.coefficient ?? 1)
    setAppreciation(row.appreciation ?? '')
  }, [row])

  async function save() {
    setSaving(true)
    await onSave({ note, coefficient, appreciation }, (/* ok */) => setSaving(false))
  }

  return (
    <tr className="hover:bg-gray-50">
      <td className="px-4 py-2 text-sm">{row.prenom} {row.nom}</td>
      <td className="px-4 py-2">
        <input
          type="number"
          min={0}
          max={20}
          step={0.5}
          className="input w-28"
          value={note}
          onChange={(e) => setNote(e.target.value)}
          placeholder="ex: 12.5"
        />
      </td>
      <td className="px-4 py-2">
        <input
          type="number"
          min={1}
          step={1}
          className="input w-24"
          value={coefficient}
          onChange={(e) => setCoefficient(e.target.value)}
        />
      </td>
      <td className="px-4 py-2">
        <input
          type="text"
          className="input w-full"
          value={appreciation}
          onChange={(e) => setAppreciation(e.target.value)}
          placeholder="Appréciation (optionnel)"
        />
      </td>
      <td className="px-4 py-2 text-right">
        <div className="flex items-center justify-end gap-3">
          <button className="text-red-600" onClick={onDelete} disabled={saving}>Supprimer</button>
          <button className="btn btn-primary" onClick={save} disabled={saving}>{saving ? 'Enregistrement…' : 'Enregistrer'}</button>
        </div>
      </td>
    </tr>
  )
}
