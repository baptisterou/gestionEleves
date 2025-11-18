import React, { useEffect, useMemo, useState } from 'react'
import { FolderPlus, Pencil, Trash2 } from 'lucide-react'
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
  const [form, setForm] = useState({ intituleMatiere: '', idEnseignant: '' })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')

  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  // Liste des enseignants pour le dropdown
  const [enseignants, setEnseignants] = useState([])
  const [loadingEnseignants, setLoadingEnseignants] = useState(false)

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

  async function loadEnseignants() {
    setLoadingEnseignants(true)
    try {
      const res = await api.listUsers({ page: 0, size: 1000 })
      const list = res?.content || []
      const onlyTeachers = list.filter(u => u.role === 'ENSEIGNANT')
      setEnseignants(onlyTeachers.map(u => ({ id: u.idUtilisateur, label: `${u.prenom} ${u.nom} (${u.email})` })))
    } catch (e) {
      // silencieux mais on pourrait afficher une erreur locale
    } finally {
      setLoadingEnseignants(false)
    }
  }

  useEffect(() => {
    load()
  }, [page, size])

  useEffect(() => {
    loadEnseignants()
  }, [])

  const columns = useMemo(() => ([
    { key: 'idMatiere', header: 'ID' },
    { key: 'intituleMatiere', header: 'Intitulé' },
    {
      key: 'enseignant',
      header: 'Enseignant',
      render: (_v, row) => {
        const id = row.idEnseignant ?? row.enseignant?.idUtilisateur
        const opt = enseignants.find((e) => e.id === id)
        // Affiche "Prénom Nom" si connu, sinon rien (ou l'ID en secours)
        return opt ? opt.label.replace(/\s*\([^)]*\)$/, '') : (id ?? '')
      },
    },
    {
      key: 'actions', header: 'Actions', accessor: (row) => row, render: (_v, row) => (
        <div className="flex items-center gap-2">
          <button onClick={() => startEdit(row)}><Pencil className="text-gray-500"/></button>
          <button onClick={() => askDelete(row)}><Trash2 className="text-red-600"/></button>
        </div>
      )
    }
  ]), [enseignants])

  function startCreate() {
    setEditing(null)
    setForm({ intituleMatiere: '', idEnseignant: '' })
    setFormError('')
    setOpenModal(true)
  }

  function startEdit(row) {
    setEditing(row)
    setForm({
      intituleMatiere: row.intituleMatiere || '',
      idEnseignant: row.idEnseignant ? String(row.idEnseignant) : '',
    })
    setFormError('')
    setOpenModal(true)
  }

  function askDelete(row) {
    setToDelete(row)
    setConfirmOpen(true)
  }

  function validate(values) {
    if (!values.intituleMatiere?.trim()) return "L'intitulé est requis"
    if (values.idEnseignant && !Number.isFinite(Number(values.idEnseignant))) return "L'identifiant enseignant doit être numérique"
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
        intituleMatiere: form.intituleMatiere.trim(),
        ...(form.idEnseignant ? { idEnseignant: Number(form.idEnseignant) } : {}),
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
    <div className="bg-white rounded-xl p-6 ">
      <div className="space-y-4">
        <header className="flex flex-wrap items-end justify-between gap-3">
          <div>
            <h1 className="text-lg font-semibold">Matières</h1>
            <p className="text-sm text-gray-600">Liste des Matières.</p>
          </div>
        <div className="flex items-center gap-2 transition-transform duration-300 hover:scale-105">
          <button className="btn btn-primary" onClick={startCreate}>
            <FolderPlus  className="mr-2"/> Nouveau
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
            <label className="label">Intitulé</label>
            <input className="input" value={form.intituleMatiere} onChange={(e) => setForm({ ...form, intituleMatiere: e.target.value })} required />
          </div>
          <div>
            <label className="label">Enseignant</label>
            <select className="input" value={form.idEnseignant} onChange={(e) => setForm({ ...form, idEnseignant: e.target.value })} disabled={loadingEnseignants}>
              <option value="">(Optionnel) Sélectionner un enseignant</option>
              {enseignants.map(opt => (
                <option key={opt.id} value={opt.id}>{opt.label}</option>
              ))}
            </select>
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
    </div>
  )
}
