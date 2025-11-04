import React from 'react'
import { useAuth } from '../../lib/useAuth'

export default function AdminHome() {
  const { user } = useAuth()
  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Espace Admin</h1>
      <div className="card p-4">
        <p className="text-sm text-gray-700">Bienvenue {user?.prenom} {user?.nom}. Utilisez le menu pour gérer les utilisateurs, élèves, classes, matières et inscriptions.</p>
      </div>
      <div className="grid grid-cols-1 gap-3 sm:grid-cols-3">
        <Shortcut title="Utilisateurs" to="/admin/utilisateurs" />
        <Shortcut title="Élèves" to="/admin/eleves" />
        <Shortcut title="Statistiques" to="/admin/stats" />
      </div>
    </div>
  )
}

function Shortcut({ title, to }) {
  return (
    <a href={to} className="rounded-md border border-gray-200 p-4 hover:bg-gray-50">
      <div className="font-medium">{title}</div>
    </a>
  )
}
