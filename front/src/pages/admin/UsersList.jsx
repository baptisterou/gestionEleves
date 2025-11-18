import React, { useEffect, useMemo, useState } from 'react'
import { Pencil, Trash2, UserRoundPlus } from 'lucide-react'
import Table from '../../components/Table'
import Pagination from '../../components/Pagination'
import Modal from '../../components/Modal'
import ConfirmDialog from '../../components/ConfirmDialog'
import { useToast } from '../../lib/useToast'
import { api } from '../../lib/api'

const ROLES = ['ADMIN', 'ENSEIGNANT', 'RESPONSABLE']

export default function UsersList() {
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(Number(import.meta.env.VITE_PAGE_SIZE || 20))
  const [role, setRole] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [data, setData] = useState({ content: [], totalPages: 1, number: 0, size: size })

  const { show } = useToast()

  const [openModal, setOpenModal] = useState(false)
  const [editing, setEditing] = useState(null) // if set, edit mode
  const [form, setForm] = useState({ nom: '', prenom: '', email: '', numTel: '', dateNaissance: '', role: 'RESPONSABLE', password: '', confirmPassword: '' })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')

  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  async function load() {
    setLoading(true)
    setError('')
    try {
      const res = await api.listUsers({ page, size, ...(role ? { role } : {}) })
      setData(res)
    } catch (e) {
      setError(e?.message || 'Erreur de chargement')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    load()
  }, [page, size, role])

  const columns = useMemo(() => ([
    { key: 'idUtilisateur', header: 'ID' },
    { key: 'nom', header: 'Nom' },
    { key: 'prenom', header: 'Prénom' },
    { key: 'email', header: 'Email' },
    { key: 'numTel', header: 'Téléphone' },
    { key: 'role', header: 'Rôle' },
    {
      key: 'actions', header: 'Actions', accessor: (row) => row, render: (_v, row) => (
        <div className="flex items-center gap-2">
          <button className="text-black" onClick={() => startEdit(row)}><Pencil className="text-gray-500"/></button>
          <button className="text-red-600" onClick={() => askDelete(row)}><Trash2 /></button>
        </div>
      )
    }
  ]), [])

  function startCreate() {
    setEditing(null)
    setForm({ nom: '', prenom: '', email: '', numTel: '', dateNaissance: '', role: 'RESPONSABLE', password: '', confirmPassword: '' })
    setFormError('')
    setOpenModal(true)
  }

  function startEdit(row) {
    setEditing(row)
    setForm({
      nom: row.nom || '',
      prenom: row.prenom || '',
      email: row.email || '',
      numTel: row.numTel || '',
      dateNaissance: row.dateNaissance ? new Date(row.dateNaissance).toISOString().slice(0, 10) : '',
      role: row.role || 'RESPONSABLE',
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
    if (!values.email?.trim()) return "L'email est requis"
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(values.email)) return 'Email invalide'
    if (!values.role) return 'Le rôle est requis'
    if (!editing) {
      if (!values.password) return 'Le mot de passe est requis'
      const strong = /^(?=.*[A-Za-z])(?=.*\d).{8,}$/.test(values.password)
      if (!strong) return 'Le mot de passe doit contenir au moins 8 caractères, dont au moins une lettre et un chiffre'
      if (values.password !== values.confirmPassword) return 'La confirmation ne correspond pas au mot de passe'
    }
    return ''
  }

  async function saveUser(e) {
    e?.preventDefault()
    const err = validate(form)
    if (err) {
      setFormError(err)
      return
    }
    setSaving(true)
    try {
      const basePayload = {
        nom: form.nom.trim(),
        prenom: form.prenom.trim(),
        email: form.email.trim(),
        numTel: form.numTel?.trim() || '',
        dateNaissance: form.dateNaissance || null,
      }
      if (editing) {
        // 1) Mettre à jour les infos générales (sans le rôle)
        await api.updateUser(editing.idUtilisateur, basePayload)
        // 2) Si le rôle a changé, appeler l'endpoint dédié
        const previousRole = editing.role || 'RESPONSABLE'
        if (previousRole !== form.role) {
          await api.updateUserRole(editing.idUtilisateur, { role: form.role })
        }
        show('Utilisateur mis à jour', { type: 'success' })
      } else {
        // Création ADMIN avec rôle explicite
        const createPayload = { ...basePayload, motDePasse: form.password, role: form.role }
        await api.createUserAdmin(createPayload)
        show('Utilisateur créé', { type: 'success' })
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
      await api.deleteUser(toDelete.idUtilisateur)
      show('Utilisateur supprimé', { type: 'success' })
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
            <h1 className="text-lg font-semibold">Utilisateurs</h1>
            <p className="text-sm text-gray-600">Liste des utilisateurs. Filtrez par rôle si nécessaire.</p>
          </div>
          <div className="flex items-center gap-2 ">
            <label className="label">Rôle</label>
            <select className="input w-40" value={role} onChange={(e) => { setPage(0); setRole(e.target.value) }}>
              <option value="">Tous</option>
              {ROLES.map(r => <option key={r} value={r}>{r}</option>)}
            </select>
            <div className="transition-transform duration-300 hover:scale-105">
            <button className="btn btn-primary" onClick={startCreate}>
              <UserRoundPlus className="mr-2"/>Nouveau
            </button>
            </div>
          </div>
        </header>

        {loading && <div className="text-sm text-gray-500">Chargement…</div>}
        {error && <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>}

        {!loading && !error && (
          <>
           <div className="overflow-hidden rounded-xl">
            <Table columns={columns} data={data?.content || []} keyField="idUtilisateur" />
            <Pagination
              page={data?.number ?? page}
              size={data?.size ?? size}
              totalElements={data?.totalElements ?? 0}
              onPageChange={(p) => setPage(p)}
            />
            </div>
          </>
        )}

        <Modal
          open={openModal}
          onClose={() => { if (!saving) setOpenModal(false) }}
          title={editing ? 'Éditer un utilisateur' : 'Créer un utilisateur'}
          actions={(
            <>
              <button className="btn btn-outline" onClick={() => setOpenModal(false)} disabled={saving}>Annuler</button>
              <button className="btn btn-primary" onClick={saveUser} disabled={saving}>{saving ? 'Enregistrement…' : 'Enregistrer'}</button>
            </>
          )}
        >
          {formError && <div className="mb-3 rounded-md border border-red-200 bg-red-50 p-2 text-sm text-red-700 rounded-xl">{formError}</div>}
          <form onSubmit={saveUser} className="space-y-3">
            <div>
              <label className="label">Nom</label>
              <input className="input" value={form.nom} onChange={(e) => setForm({ ...form, nom: e.target.value })} required />
            </div>
            <div>
              <label className="label">Prénom</label>
              <input className="input" value={form.prenom} onChange={(e) => setForm({ ...form, prenom: e.target.value })} required />
            </div>
            <div>
              <label className="label">Email</label>
              <input type="email" className="input" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required />
            </div>
            <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
              <div>
                <label className="label">Téléphone</label>
                <input className="input" value={form.numTel} onChange={(e) => setForm({ ...form, numTel: e.target.value })} />
              </div>
              <div>
                <label className="label">Date de naissance</label>
                <input type="date" className="input" value={form.dateNaissance} onChange={(e) => setForm({ ...form, dateNaissance: e.target.value })} />
              </div>
            </div>
            <div>
              <label className="label">Rôle</label>
              <select className="input" value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value })}>
                {ROLES.map(r => <option key={r} value={r}>{r}</option>)}
              </select>
            </div>

            {!editing && (
              <>
                <div>
                  <label className="label">Mot de passe</label>
                  <input
                    type="password"
                    className="input"
                    value={form.password}
                    onChange={(e) => setForm({ ...form, password: e.target.value })}
                    placeholder="Au moins 8 caractères, 1 lettre et 1 chiffre"
                    required
                  />
                </div>
                <div>
                  <label className="label">Confirmer le mot de passe</label>
                  <input
                    type="password"
                    className="input"
                    value={form.confirmPassword}
                    onChange={(e) => setForm({ ...form, confirmPassword: e.target.value })}
                    placeholder="Retapez le mot de passe"
                    required
                  />
                </div>
                <p className="text-xs text-gray-500">Le mot de passe doit contenir au moins 8 caractères, dont au moins une lettre et un chiffre.</p>
              </>
            )}
          </form>
        </Modal>

        <ConfirmDialog
          open={confirmOpen}
          title="Supprimer l’utilisateur"
          message={toDelete ? `Confirmez la suppression de ${toDelete.prenom} ${toDelete.nom} (${toDelete.email}) ?` : ''}
          confirmLabel="Supprimer"
          cancelLabel="Annuler"
          onConfirm={confirmDelete}
          onCancel={() => setConfirmOpen(false)}
        />
      </div>
    </div>
  )
}
