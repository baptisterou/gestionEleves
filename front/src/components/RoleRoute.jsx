import React from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '../lib/useAuth'

export default function RoleRoute({ allow = [], children }) {
  const { user, loading } = useAuth()
  const location = useLocation()

  if (loading) {
    return <div className="text-center text-gray-500">Chargement…</div>
  }

  if (!user) {
    return <Navigate to="/login" replace state={{ from: location }} />
  }

  if (allow.length && !allow.includes(user.role)) {
    // Refusé: redirige vers la page d'accueil du rôle de l'utilisateur
    const dest = user.role === 'ADMIN' ? '/admin' : user.role === 'ENSEIGNANT' ? '/enseignant' : user.role === 'RESPONSABLE' ? '/responsable' : '/'
    return <Navigate to={dest} replace />
  }

  return children
}
