import React, { useEffect, useMemo, useState } from 'react'
import Table from '../../components/Table'
import Pagination from '../../components/Pagination'
import Modal from '../../components/Modal'
import ConfirmDialog from '../../components/ConfirmDialog'
import { useToast } from '../../lib/useToast'
import { api } from '../../lib/api'

export default function ClassesList() {
  const [page, setPage] = useState(0)
  const [size] = useState(Number(import.meta.env.VITE_PAGE_SIZE || 20))
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [data, setData] = useState({ content: [], totalElements: 0, number: 0, size })

  const { show } = useToast()


  const [openModal, setOpenModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState({ nom: '', niveau: '', annee: '' })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')

  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  async function load() {
    setLoading(true)
    setError('')
    try {
      const res = await api.listClasses({ page, size })
      setData(res)
    } catch (e) {
      setError(e?.message || 'Erreur de chargement')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    load()
  }, [page, size])

  const columns = useMemo(() => ([
    { key: 'idClasse', header: 'ID' },
    { key: 'nomClasse', header: 'Nom', render: (v, row) => row.nom ?? row.nomClasse ?? '' },
    { key: 'niveauClasse', header: 'Niveau', render: (v, row) => row.niveau ?? row.niveauClasse ?? '' },
    { key: 'anneeScolaire', header: 'Année', render: (v, row) => row.anneeScolaire ?? row.annee ?? '' },
    {
      key: 'actions', header: 'Actions', accessor: (row) => row, render: (_v, row) => (
        <div className="flex items-center gap-2">
          <button className="text-primary" onClick={() => startEdit(row)}>Éditer</button>
          <button className="text-red-600" onClick={() => askDelete(row)}>Supprimer</button>
        </div>
      )
    }
  ]), [])

  function startCreate() {
    setEditing(null)
    setForm({ nom: '', niveau: '', annee: '' })
    setFormError('')
    setOpenModal(true)
  }

  function startEdit(row) {
    setEditing(row)
    setForm({
      nom: row.nom ?? row.nomClasse ?? '',
      niveau: row.niveau ?? row.niveauClasse ?? '',
      annee: row.anneeScolaire ?? row.annee ?? ''
    })
    setFormError('')
    setOpenModal(true)
  }

  function askDelete(row) {
    setToDelete(row)
    setConfirmOpen(true)
  }

  function validate(values) {
    if (!values.nom?.trim()) return 'Le nom est requis'
    if (!values.niveau?.trim()) return 'Le niveau est requis'
    if (!values.annee?.trim()) return "L'année est requise"
    if (!/^\d{4}\/\d{4}$/.test(values.annee)) return "L'année doit être au format 2024/2025"
    const [a,b] = values.annee.split('/').map(Number)
    if (b !== a + 1) return "L'année doit être au format 2024/2025 (droite = gauche + 1)"
    return ''
  }

  async function saveClasse(e) {
    e?.preventDefault()
    const err = validate(form)
    if (err) {
      setFormError(err)
      return
    }
    setSaving(true)
    try {
      const payload = {
        nomClasse: form.nom.trim(),
        niveauClasse: form.niveau.trim(),
        anneeScolaire: form.annee.trim(),
      }
      if (editing) {
        await api.updateClasse(editing.idClasse, payload)
        show('Classe mise à jour', { type: 'success' })
      } else {
        await api.createClasse(payload)
        show('Classe créée', { type: 'success' })
      }
      setOpenModal(false)
      setEditing(null)
      await load()
    } catch (e) {
      setFormError(e?.message || 'Erreur lors de la sauvegarde')
      show(e?.message || 'Erreur lors de la sauvegarde', { type: 'error' })
    } finally {
      setSaving(false)
    }
  }

  async function confirmDelete() {
    if (!toDelete) return
    try {
      await api.deleteClasse(toDelete.idClasse)
      show('Classe supprimée', { type: 'success' })
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
          <h1 className="text-lg font-semibold">Classes</h1>
          <p className="text-sm text-gray-600">Liste des classes.</p>
        </div>
        <div className="flex items-center gap-2">
          <button className="btn btn-primary" onClick={startCreate}>
            Nouvelle classe
          </button>
        </div>
      </header>

      {loading && <div className="text-sm text-gray-500">Chargement…</div>}
      {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

      {!loading && !error && (
        <>
          <Table columns={columns} data={data?.content || []} keyField="idClasse" />
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
        title={editing ? 'Éditer une classe' : 'Créer une classe (nom, niveau, année)'}
        actions={(
          <>
            <button className="btn btn-outline" onClick={() => setOpenModal(false)} disabled={saving}>Annuler</button>
            <button className="btn btn-primary" onClick={saveClasse} disabled={saving}>{saving ? 'Enregistrement…' : 'Enregistrer'}</button>
          </>
        )}
      >
        {formError && <div className="mb-3 rounded-md border border-red-200 bg-red-50 p-2 text-sm text-red-700">{formError}</div>}
        <form onSubmit={saveClasse} className="space-y-3">
          <div>
            <label className="label">Nom</label>
            <input className="input" value={form.nom} onChange={(e) => setForm({ ...form, nom: e.target.value })} required />
          </div>
          <div>
            <label className="label">Niveau</label>
            <input className="input" value={form.niveau} onChange={(e) => setForm({ ...form, niveau: e.target.value })} required />
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
        title="Supprimer la classe"
        message={toDelete ? `Confirmez la suppression de la classe "${toDelete.nom}" (ID ${toDelete.idClasse}) ?` : ''}
        confirmLabel="Supprimer"
        cancelLabel="Annuler"
        onConfirm={confirmDelete}
        onCancel={() => setConfirmOpen(false)}
      />
    </div>
  )
}
