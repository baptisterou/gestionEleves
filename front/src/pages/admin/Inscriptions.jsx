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
function currentSchoolYear() {
  const d = new Date()
  const y = d.getMonth() >= 7 ? d.getFullYear() : d.getFullYear() - 1
  return `${y}/${y + 1}`
}
function schoolYears(count = 5) {
  const now = new Date()
  const start = now.getMonth() >= 7 ? now.getFullYear() : now.getFullYear() - 1
  return Array.from({ length: count }, (_, i) => `${start + i}/${start + i + 1}`)
}
function yearToCanonicalDate(annee) {
  // map "YYYY/YYYY+1" -> YYYY-09-01
  const [left] = annee.split('/')
  return `${left}-09-01`
}

export default function Inscriptions() {
  const [page, setPage] = useState(0)
  const [size] = useState(Number(import.meta.env.VITE_PAGE_SIZE || 20))
  const [annee, setAnnee] = useState(currentSchoolYear())
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [data, setData] = useState({ content: [], totalElements: 0, number: 0, size })

  const { show } = useToast()

  const [eleves, setEleves] = useState([])
  const [me, setMe] = useState(null)
  const [users, setUsers] = useState([])

  const [openModal, setOpenModal] = useState(false)
  const [form, setForm] = useState({ eleveId: '', annee: currentSchoolYear() })
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState('')

  const [confirmOpen, setConfirmOpen] = useState(false)
  const [toDelete, setToDelete] = useState(null)

  async function load() {
    setLoading(true)
    setError('')
    try {
      const res = await api.listInscriptions({}) // backend returns a simple list
      const list = Array.isArray(res) ? res : res?.content || []
      // compute school year from dateInscrip for filtering
      const withYear = list.map((it) => {
        const d = it.dateInscrip ? new Date(it.dateInscrip) : null
        const y = d ? `${d.getFullYear()}/${d.getFullYear() + 1}` : ''
        return { ...it, _annee: y }
      })
      const filtered = annee ? withYear.filter((it) => it._annee === annee) : withYear
      const start = page * size
      const end = start + size
      setData({ content: filtered.slice(start, end), totalElements: filtered.length, number: page, size })
    } catch (e) {
      setError(e?.message || 'Erreur de chargement')
    } finally {
      setLoading(false)
    }
  }

  async function loadOptions() {
    try {
      const [els, meRes, usrs] = await Promise.all([
        api.listEleves({ page: 0, size: 1000 }),
        api.me(),
        api.listUsers({ page: 0, size: 1000 }),
      ])
      setEleves(els?.content || [])
      setMe(meRes)
      setUsers(usrs?.content || [])
    } catch (e) {
    }
  }

  useEffect(() => {
    load()
  }, [page, size, annee])

  useEffect(() => {
    loadOptions()
  }, [])

  const eleveNameById = useMemo(() => {
    const map = new Map()
    for (const e of eleves || []) {
      const label = [e.prenom, e.nom].filter(Boolean).join(' ').trim()
      map.set(e.idEleve, label || `#${e.idEleve}`)
    }
    return map
  }, [eleves])

  const userNameById = useMemo(() => {
    const map = new Map()
    for (const u of users || []) {
      const label = [u.prenom, u.nom].filter(Boolean).join(' ').trim()
      map.set(u.idUtilisateur, label || `#${u.idUtilisateur}`)
    }
    if (me?.idUtilisateur && me?.prenom) {
      const label = [me.prenom, me.nom].filter(Boolean).join(' ').trim()
      map.set(me.idUtilisateur, label || `#${me.idUtilisateur}`)
    }
    return map
  }, [users, me])

  const columns = useMemo(() => ([
    { key: 'eleve', header: 'Élève', render: (_v, row) => eleveNameById.get(row.eleveId) ?? row.eleveId },
    { key: 'utilisateur', header: 'Inscrit par', render: (_v, row) => userNameById.get(row.utilisateurId) ?? row.utilisateurId },
    { key: 'dateInscrip', header: 'Date' },
    { key: 'annee', header: 'Année', accessor: (row) => row._annee || '' },
    {
      key: 'actions', header: 'Actions', accessor: (row) => row, render: (_v, row) => (
        <div className="flex items-center gap-2">
          <button className="text-red-600" onClick={() => askDelete(row)}>Supprimer</button>
        </div>
      )
    }
  ]), [eleveNameById, userNameById])

  function startCreate() {
    setForm({ eleveId: '', annee })
    setFormError('')
    setOpenModal(true)
  }

  function askDelete(row) {
    setToDelete(row)
    setConfirmOpen(true)
  }

  function validate(values) {
    if (!values.eleveId) return "L'élève est requis"
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
      const payload = { eleveId: Number(form.eleveId), utilisateurId: me?.idUtilisateur, dateInscrip: yearToCanonicalDate(form.annee) }
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
      await api.deleteInscription(toDelete.eleveId, toDelete.utilisateurId)
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
          <select className="input w-40" value={annee} onChange={(e) => { setPage(0); setAnnee(e.target.value) }}>
            {schoolYears(5).map(y => (
              <option key={y} value={y}>{y}</option>
            ))}
          </select>
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
            <label className="label">Année scolaire</label>
            <select className="input" value={form.annee} onChange={(e) => setForm({ ...form, annee: e.target.value })} required>
              {schoolYears(5).map(y => (
                <option key={y} value={y}>{y}</option>
              ))}
            </select>
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
