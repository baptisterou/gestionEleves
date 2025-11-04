import React, { useEffect, useState } from 'react'
import { api } from '../lib/api'

export default function Dashboard() {
  const [me, setMe] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    let mounted = true
    ;(async () => {
      try {
        const data = await api.me()
        if (mounted) setMe(data)
      } catch (e) {
        setError(e.message || 'Erreur de chargement')
      } finally {
        setLoading(false)
      }
    })()
    return () => {
      mounted = false
    }
  }, [])

  if (loading) {
    return <div className="text-center text-gray-500">Chargement…</div>
  }

  if (error) {
    return (
      <div className="rounded-md border border-red-200 bg-red-50 p-4 text-red-700">
        {error}
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <section className="card p-6">
        <h2 className="mb-4 text-lg font-semibold">Mon profil</h2>
        {me ? (
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
            <Info label="Nom" value={me.nom} />
            <Info label="Prénom" value={me.prenom} />
            <Info label="Email" value={me.email} />
            <Info label="Téléphone" value={me.numTel} />
            <Info label="Date de naissance" value={me.dateNaissance ? new Date(me.dateNaissance).toLocaleDateString() : ''} />
            <Info label="Rôle" value={me.role} />
          </div>
        ) : (
          <div className="text-sm text-gray-500">Aucune information</div>
        )}
      </section>

      <section className="card p-6">
        <h2 className="mb-4 text-lg font-semibold">Accès rapides</h2>
        <div className="grid grid-cols-1 gap-3 sm:grid-cols-3">
          <QuickLink title="Élèves" description="Consulter et gérer les élèves" />
          <QuickLink title="Matières" description="Consulter et gérer les matières" />
          <QuickLink title="Classes" description="Consulter et gérer les classes" />
        </div>
      </section>
    </div>
  )
}

function Info({ label, value }) {
  return (
    <div>
      <div className="text-xs uppercase tracking-wide text-gray-500">{label}</div>
      <div className="text-sm font-medium">{value || '-'}</div>
    </div>
  )
}

function QuickLink({ title, description }) {
  return (
    <div className="rounded-md border border-gray-200 p-4 hover:bg-gray-50">
      <div className="font-medium">{title}</div>
      <div className="text-sm text-gray-600">{description}</div>
    </div>
  )
}
