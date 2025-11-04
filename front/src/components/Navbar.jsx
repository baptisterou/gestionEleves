import React from 'react'
import { useNavigate } from 'react-router-dom'
import { clearToken } from '../lib/auth'
import { useAuth } from '../lib/useAuth'

export default function Navbar() {
  const navigate = useNavigate()
  const { user } = useAuth()

  const logout = () => {
    clearToken()
    navigate('/login', { replace: true })
  }

  const home = user?.role === 'ADMIN' ? '/admin' : user?.role === 'ENSEIGNANT' ? '/enseignant' : user?.role === 'RESPONSABLE' ? '/responsable' : '/'

  return (
    <header className="border-b border-gray-200 bg-white">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3">
        <a className="flex items-center gap-3" href={home}>
          <div className="flex h-9 w-9 items-center justify-center rounded-md bg-primary text-white">GE</div>
          <span className="text-lg font-semibold">Gestion Élèves</span>
        </a>
        <div className="flex items-center gap-3">
          {user && (
            <div className="text-sm text-gray-600">
              <div className="font-medium">{user.prenom} {user.nom}</div>
              <div className="text-xs uppercase tracking-wide">{user.role}</div>
            </div>
          )}
          <button className="btn btn-outline" onClick={logout}>
            Se déconnecter
          </button>
        </div>
      </div>
    </header>
  )
}
