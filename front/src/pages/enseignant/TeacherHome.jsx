import React from 'react'
import { useAuth } from '../../lib/useAuth'

export default function TeacherHome() {
  const { user } = useAuth()
  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Espace Enseignant</h1>
      <div className="card p-4">
        <p className="text-sm text-gray-700">Bonjour {user?.prenom} {user?.nom}. Accédez à la saisie des notes et aux bulletins via le menu.</p>
      </div>
    </div>
  )
}
