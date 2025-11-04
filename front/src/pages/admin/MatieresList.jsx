import React, { useEffect, useMemo, useState } from 'react'
import Table from '../../components/Table'
import Pagination from '../../components/Pagination'
import Modal from '../../components/Modal'
import ConfirmDialog from '../../components/ConfirmDialog'
import { useToast } from '../../lib/useToast'
import { api } from '../../lib/api'

export default function MatieresList() {
  const [page, setPage] = useState(0)
  const [size] = useState(Number(import.meta.env.VITE_PAGE_SIZE || 20))
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [data, setData] = useState({ content: [], totalElements: 0, number: 0, size })

  const { show } = useToast()

  const [openModal, setOpenModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState({ nom: '', coefficient: 1 })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')

  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  async function load() {
    setLoading(true)
    setError('')
    try {
      const res = await api.listMatieres({ page, size })
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
    { key: 'idMatiere', header: 'ID' },
    { key: 'nom', header: 'Nom' },
    { key: 'coefficient', header: 'Coefficient' },
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
    setForm({ nom: '', coefficient: 1 })
    setFormError('')
    setOpenModal(true)
  }

  function startEdit(row) {
    setEditing(row)
    setForm({
      nom: row.nom || '',
      coefficient: row.coefficient ?? 1,
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
    const coef = Number(values.coefficient)
    if (!Number.isFinite(coef) || coef < 1) return 'Le coefficient doit être un nombre ≥ 1'
    return ''
  }

  async function saveMatiere(e) {
    e?.preventDefault()
    const err = validate(form)
    if (err) {
      setFormError(err)
      return
    }
    setSaving(true)
    try {
      const payload = {
        nom: form.nom.trim(),
        coefficient: Number(form.coefficient),
      }
      if (editing) {
        await api.updateMatiere(editing.idMatiere, payload)
        show('Matière mise à jour', { type: 'success' })
      } else {
        await api.createMatiere(payload)
        show('Matière créée', { type: 'success' })
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
      await api.deleteMatiere(toDelete.idMatiere)
      show('Matière supprimée', { type: 'success' })
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
          <h1 className="text-lg font-semibold">Matières</h1>
          <p className="text-sm text-gray-600">Liste des matières.</p>
        </div>
        <div className="flex items-center gap-2">
          <button className="btn btn-primary" onClick={startCreate}>
            Nouvelle matière
          </button>
        </div>
      </header>

      {loading && <div className="text-sm text-gray-500">Chargement…</div>}
      {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

      {!loading && !error && (
        <>
          <Table columns={columns} data={data?.content || []} keyField="idMatiere" />
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
        title={editing ? 'Éditer une matière' : 'Créer une matière'}
        actions={(
          <>
            <button className="btn btn-outline" onClick={() => setOpenModal(false)} disabled={saving}>Annuler</button>
            <button className="btn btn-primary" onClick={saveMatiere} disabled={saving}>{saving ? 'Enregistrement…' : 'Enregistrer'}</button>
          </>
        )}
      >
        {formError && <div className="mb-3 rounded-md border border-red-200 bg-red-50 p-2 text-sm text-red-700">{formError}</div>}
        <form onSubmit={saveMatiere} className="space-y-3">
          <div>
            <label className="label">Nom</label>
            <input className="input" value={form.nom} onChange={(e) => setForm({ ...form, nom: e.target.value })} required />
          </div>
          <div>
            <label className="label">Coefficient</label>
            <input type="number" min={1} step={1} className="input" value={form.coefficient} onChange={(e) => setForm({ ...form, coefficient: e.target.value })} required />
          </div>
        </form>
      </Modal>

      <ConfirmDialog
        open={confirmOpen}
        title="Supprimer la matière"
        message={toDelete ? `Confirmez la suppression de la matière "${toDelete.nom}" (ID ${toDelete.idMatiere}) ?` : ''}
        confirmLabel="Supprimer"
        cancelLabel="Annuler"
        onConfirm={confirmDelete}
        onCancel={() => setConfirmOpen(false)}
      />
    </div>
  )
}
