/*
 * Composant pour protéger les routes selon le rôle de l'utilisateur
 * Vérifie si l'utilisateur a les autorisations nécessaires
 * Redirige vers la page appropriée en cas d'accès refusé
 */
import React from 'react'
import { Navigate, useLocation } from 'react-router-dom'
import { useAuth } from '../lib/useAuth'

export default function RoleRoute({ allow = [], children }) {
  const { user, loading } = useAuth()
  const location = useLocation()

  // Affiche un indicateur de chargement pendant la vérification
  if (loading) {
    return <div className="text-center text-gray-500">Chargement…</div>
  }

  // Redirige vers la page de connexion si l'utilisateur n'est pas authentifié
  if (!user) {
    return <Navigate to="/login" replace state={{ from: location }} />
  }

  // Vérifie si le rôle de l'utilisateur est autorisé
  if (allow.length && !allow.includes(user.role)) {
    // Refusé: redirige vers la page d'accueil du rôle de l'utilisateur
    const dest = user.role === 'ADMIN' ? '/admin' : user.role === 'ENSEIGNANT' ? '/enseignant' : user.role === 'RESPONSABLE' ? '/responsable' : '/'
    return <Navigate to={dest} replace />
  }

  // Si tout est bon, affiche le contenu
  return children
}
