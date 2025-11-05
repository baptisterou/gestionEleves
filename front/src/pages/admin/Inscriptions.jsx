import React, { useEffect, useMemo, useState } from 'react'
import Table from '../../components/Table'
import Pagination from '../../components/Pagination'
import Modal from '../../components/Modal'
import ConfirmDialog from '../../components/ConfirmDialog'
import { useToast } from '../../lib/useToast'
import { api } from '../../lib/api'

function isValidSchoolYear(value) {
  if (!/^\d{4}\/\d{4}$/.test(value)) return false
  const [a, b] = value.split('/').map(Number)
  return b === a + 1
}

export default function Inscriptions() {
  const [page, setPage] = useState(0)
  const [size] = useState(Number(import.meta.env.VITE_PAGE_SIZE || 20))
  const [classeId, setClasseId] = useState('')
  const [annee, setAnnee] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [data, setData] = useState({ content: [], totalElements: 0, number: 0, size })

  const { show } = useToast()
    
  const [classes, setClasses] = useState([])
  const [eleves, setEleves] = useState([])
    
  const [openModal, setOpenModal] = useState(false)
  const [form, setForm] = useState({ eleveId: '', classeId: '', annee: '' })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')
    
  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  async function load() {
    setLoading(true)
    setError('')
    try {
      const res = await api.listInscriptions({ page, size, ...(classeId ? { classeId } : {}), ...(annee ? { annee } : {}) })
      setData(res)
    } catch (e) {
      setError(e?.message || 'Erreur de chargement')
    } finally {
      setLoading(false)
    }
  }

  async function loadOptions() {
    try {
      const [cls, els] = await Promise.all([
        api.listClasses({ page: 0, size: 100 }),
        api.listEleves({ page: 0, size: 100 }),
      ])
      setClasses(cls?.content || [])
      setEleves(els?.content || [])
    } catch (e) {
    }
  }

  useEffect(() => {
    load()
  }, [page, size, classeId, annee])

  useEffect(() => {
    loadOptions()
  }, [])

  const columns = useMemo(() => ([
    { key: 'id', header: 'ID' },
    { key: 'eleve', header: 'Élève', accessor: (row) => row?.eleve?.prenom && row?.eleve?.nom ? `${row.eleve.prenom} ${row.eleve.nom}` : (row?.eleveId ?? '') },
    { key: 'classe', header: 'Classe', accessor: (row) => row?.classe?.nom ?? row?.classeId ?? '' },
    { key: 'annee', header: 'Année' },
    {
      key: 'actions', header: 'Actions', accessor: (row) => row, render: (_v, row) => (
        <div className="flex items-center gap-2">
          <button className="text-red-600" onClick={() => askDelete(row)}>Supprimer</button>
        </div>
      )
    }
  ]), [])

  function startCreate() {
    setForm({ eleveId: '', classeId: classeId || '', annee: annee || '' })
    setFormError('')
    setOpenModal(true)
  }

  function askDelete(row) {
    setToDelete(row)
    setConfirmOpen(true)
  }

  function validate(values) {
    if (!values.eleveId) return "L'élève est requis"
    if (!values.classeId) return 'La classe est requise'
    if (!values.annee) return "L'année est requise"
    if (!isValidSchoolYear(values.annee)) return "L'année doit être au format 2024/2025 (année droite = année gauche + 1)"
    return ''
  }

  async function saveInscription(e) {
    e?.preventDefault()
    const err = validate(form)
    if (err) {
      setFormError(err)
      return
    }
    setSaving(true)
    try {
      const payload = { eleveId: Number(form.eleveId), classeId: Number(form.classeId), annee: form.annee }
      await api.createInscription(payload)
      show('Inscription créée', { type: 'success' })
      setOpenModal(false)
      await load()
    } catch (e) {
      setFormError(e?.message || "Erreur lors de l'enregistrement")
      show(e?.message || "Erreur lors de l'enregistrement", { type: 'error' })
    } finally {
      setSaving(false)
    }
  }

  async function confirmDelete() {
    if (!toDelete) return
    try {
      const id = toDelete.id ?? toDelete.idInscription ?? toDelete.idInscrire
      await api.deleteInscription(id)
      show('Inscription supprimée', { type: 'success' })
      await load()
    } catch (e) {
      show(e?.message || 'Erreur lors de la suppression', { type: 'error' })
    } finally {
      setConfirmOpen(false)
      setToDelete(null)
    }
  }

  return (
    <div className="space-y-4">
      <header className="flex flex-wrap items-end justify-between gap-3">
        <div>
          <h1 className="text-lg font-semibold">Inscriptions</h1>
          <p className="text-sm text-gray-600">Affectez des élèves aux classes pour une année scolaire (format 2024/2025).</p>
        </div>
        <div className="flex items-center gap-2">
          <select className="input" value={classeId} onChange={(e) => { setPage(0); setClasseId(e.target.value) }}>
            <option value="">Toutes les classes</option>
            {classes.map(c => (
              <option key={c.idClasse} value={c.idClasse}>
                {(c.nomClasse ?? c.nom) + ((c.niveauClasse || c.niveau) ? ` (${c.niveauClasse ?? c.niveau})` : '')}
              </option>
            ))}
          </select>
          <input
            className="input w-40"
            placeholder="Année (2024/2025)"
            value={annee}
            onChange={(e) => { setPage(0); setAnnee(e.target.value) }}
          />
          <button className="btn btn-primary" onClick={startCreate}>
            Nouvelle inscription
          </button>
        </div>
      </header>

      {loading && <div className="text-sm text-gray-500">Chargement…</div>}
      {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

      {!loading && !error && (
        <>
          <Table columns={columns} data={data?.content || []} />
          <Pagination
            page={data?.number ?? page}
            size={data?.size ?? size}
            totalElements={data?.totalElements ?? 0}
            onPageChange={(p) => setPage(p)}
          />
        </>
      )}
        
      <Modal
        open={openModal}
        onClose={() => { if (!saving) setOpenModal(false) }}
        title={'Créer une inscription'}
        actions={(
          <>
            <button className="btn btn-outline" onClick={() => setOpenModal(false)} disabled={saving}>Annuler</button>
            <button className="btn btn-primary" onClick={saveInscription} disabled={saving}>{saving ? 'Enregistrement…' : 'Enregistrer'}</button>
          </>
        )}
      >
        {formError && <div className="mb-3 rounded-md border border-red-200 bg-red-50 p-2 text-sm text-red-700">{formError}</div>}
        <form onSubmit={saveInscription} className="space-y-3">
          <div>
            <label className="label">Élève</label>
            <select className="input" value={form.eleveId} onChange={(e) => setForm({ ...form, eleveId: e.target.value })} required>
              <option value="">Sélectionner un élève</option>
              {eleves.map(el => (
                <option key={el.idEleve} value={el.idEleve}>{el.prenom} {el.nom} (#{el.idEleve})</option>
              ))}
            </select>
          </div>
          <div>
            <label className="label">Classe</label>
            <select className="input" value={form.classeId} onChange={(e) => setForm({ ...form, classeId: e.target.value })} required>
              <option value="">Sélectionner une classe</option>
              {classes.map(c => (
                <option key={c.idClasse} value={c.idClasse}>
                  {(c.nomClasse ?? c.nom) + ((c.niveauClasse || c.niveau) ? ` (${c.niveauClasse ?? c.niveau})` : '')}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label className="label">Année scolaire</label>
            <input
              className="input"
              placeholder="2024/2025"
              value={form.annee}
              onChange={(e) => setForm({ ...form, annee: e.target.value })}
              required
            />
            <p className="mt-1 text-xs text-gray-500">Format: YYYY/YYYY+1 (ex: 2024/2025)</p>
          </div>
        </form>
      </Modal>
        
      <ConfirmDialog
        open={confirmOpen}
        title="Supprimer l’inscription"
        message={toDelete ? `Confirmez la suppression de l’inscription #${toDelete.id ?? toDelete.idInscription ?? toDelete.idInscrire} ?` : ''}
        confirmLabel="Supprimer"
        cancelLabel="Annuler"
        onConfirm={confirmDelete}
        onCancel={() => setConfirmOpen(false)}
      />
    </div>
  )
}
