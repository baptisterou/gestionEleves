import React, { useEffect, useMemo, useState } from 'react'
import { UserRoundPlus } from 'lucide-react'
import Table from '../../components/Table'
import Pagination from '../../components/Pagination'
import Modal from '../../components/Modal'
import ConfirmDialog from '../../components/ConfirmDialog'
import { useToast } from '../../lib/useToast'
import { api } from '../../lib/api'

export default function ElevesList() {
  const [page, setPage] = useState(0)
  const [size] = useState(Number(import.meta.env.VITE_PAGE_SIZE || 20))
  const [q, setQ] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [data, setData] = useState({ content: [], totalElements: 0, number: 0, size })
  const [classe, setClasse] = useState('')
  const [classes, setClasses] = useState([])
  const { show } = useToast()


  const [openModal, setOpenModal] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState({ nom: '', prenom: '', dateNaissance: '' })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')

  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  async function load() {
    setLoading(true)
    setError('')
    try {
      const res = await api.listEleves({
        page,
        size,
        ...(q ? { q } : {}),
        ...(classe ? { classe } : {})
      })
      setData(res)
    } catch (e) {
      setError(e?.message || 'Erreur de chargement')
    } finally {
      setLoading(false)
    }
  }


useEffect(() => {
  async function loadClasses() {
    try {
      const res = await api.listClasses();
      setClasses(res.content || []);
    } catch (e) {
      console.error('Erreur lors du chargement des classes', e);
      setClasses([]);
    }
  }
  loadClasses();
}, []);

  useEffect(() => {
    load()
  }, [page, size, q])

  const columns = useMemo(() => ([
    { key: 'idEleve', header: 'ID' },
    { key: 'nom', header: 'Nom' },
    { key: 'prenom', header: 'Prénom' },
    { key: 'dateNaissance', header: 'Naissance', render: (v) => v ? new Date(v).toLocaleDateString() : '' },
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
    setForm({ nom: '', prenom: '', dateNaissance: '' })
    setFormError('')
    setOpenModal(true)
  }

  function startEdit(row) {
    setEditing(row)
    setForm({
      nom: row.nom || '',
      prenom: row.prenom || '',
      dateNaissance: row.dateNaissance ? new Date(row.dateNaissance).toISOString().slice(0, 10) : '',
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
    if (!values.prenom?.trim()) return 'Le prénom est requis'
    if (values.dateNaissance && !/^\d{4}-\d{2}-\d{2}$/.test(values.dateNaissance)) return 'Date invalide'
    return ''
  }

  async function saveEleve(e) {
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
        prenom: form.prenom.trim(),
        dateNaissance: form.dateNaissance || null,
      }
      if (editing) {
        await api.updateEleve(editing.idEleve, payload)
        show('Élève mis à jour', { type: 'success' })
      } else {
        await api.createEleve(payload)
        show('Élève créé', { type: 'success' })
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
      await api.deleteEleve(toDelete.idEleve)
      show('Élève supprimé', { type: 'success' })
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
            <h1 className="text-lg font-semibold">Elèves</h1>
            <p className="text-sm text-gray-600">Liste des Elèves. Filtrez par classes si nécessaire.</p>
          </div>
          <div className="flex items-center gap-2 ">
            <label className="label">Classe</label>
            <select className="input w-40" value={classe} onChange={(e) => { setPage(0); setClasse(e.target.value) }}>
              <option value="">Tous</option>
              {classes.map(c => <option key={c} value={c}>{c}</option>)}
            </select>
            <button className="btn btn-primary" onClick={startCreate}>
              <UserRoundPlus className="mr-2" />Nouveau
            </button>
          </div>
        </header>

        {loading && <div className="text-sm text-gray-500">Chargement…</div>}
        {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

        {!loading && !error && (
          <>
            <Table columns={columns} data={data?.content || []} keyField="idEleve" />
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
          title={editing ? 'Éditer un élève' : 'Créer un élève'}
          actions={(
            <>
              <button className="btn btn-outline" onClick={() => setOpenModal(false)} disabled={saving}>Annuler</button>
              <button className="btn btn-primary" onClick={saveEleve} disabled={saving}>{saving ? 'Enregistrement…' : 'Enregistrer'}</button>
            </>
          )}
        >
          {formError && <div className="mb-3 rounded-md border border-red-200 bg-red-50 p-2 text-sm text-red-700">{formError}</div>}
          <form onSubmit={saveEleve} className="space-y-3">
            <div>
              <label className="label">Nom</label>
              <input className="input" value={form.nom} onChange={(e) => setForm({ ...form, nom: e.target.value })} required />
            </div>
            <div>
              <label className="label">Prénom</label>
              <input className="input" value={form.prenom} onChange={(e) => setForm({ ...form, prenom: e.target.value })} required />
            </div>
            <div>
              <label className="label">Date de naissance</label>
              <input type="date" className="input" value={form.dateNaissance} onChange={(e) => setForm({ ...form, dateNaissance: e.target.value })} />
            </div>
          </form>
        </Modal>

        <ConfirmDialog
          open={confirmOpen}
          title="Supprimer l’élève"
          message={toDelete ? `Confirmez la suppression de ${toDelete.prenom} ${toDelete.nom} (ID ${toDelete.idEleve}) ?` : ''}
          confirmLabel="Supprimer"
          cancelLabel="Annuler"
          onConfirm={confirmDelete}
          onCancel={() => setConfirmOpen(false)}
        />

      </div>
    </div>
  )
}

