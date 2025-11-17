/*
 * Barre de navigation principale de l'application
 * Affiche le nom de l'utilisateur, son rôle et un bouton de déconnexion
 * Redirige vers la page d'accueil appropriée selon le rôle de l'utilisateur
 */
import React from 'react'
import { useNavigate } from 'react-router-dom'
import { clearToken } from '../lib/auth'
import { useAuth } from '../lib/useAuth'
import { LogOut } from 'lucide-react'

export default function Navbar() {
  const navigate = useNavigate()
  const { user } = useAuth()

  // Fonction de déconnexion
  const logout = () => {
    clearToken()
    navigate('/login', { replace: true })
  }

  // Détermine la page d'accueil selon le rôle de l'utilisateur
  const home = user?.role === 'ADMIN' ? '/admin' : user?.role === 'ENSEIGNANT' ? '/enseignant' : user?.role === 'RESPONSABLE' ? '/responsable' : '/'

  return (
    <header className="border-b border-gray-200 bg-white">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3">
        {/* Logo et nom de l'application */}
        <a className="flex items-center gap-3" href={home}>
          <p className="flex h-9 w-9 items-center justify-center rounded-md bg-primary text-white">GE</p>
          <span className="text-lg font-semibold">Gestion Élèves</span>
        </a>
        <div className="flex items-center gap-3">
        {/* Informations de l'utilisateur et bouton de déconnexion */}
        {user && (
          <div className="flex flex-col text-sm text-black">
            <p className="font-medium">{user.prenom} {user.nom}</p>
            <p className="text-xs uppercase tracking-wide text-[#0638FF]">{user.role}</p>
            <hr className="my-2 h-0.5 bg-text-gray-700" />
            <div class="hover:scale-110 transition-transform">
              <button
                className="btn text-sm text-[#C80000] flex items-center gap-2"
                onClick={logout}>
                <LogOut className="w-5 h-5" />
                Déconnexion
              </button>
            </div>
          </div>
        )}
        </div>
      </div>
    </header>
  )
}
