import React, { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { api } from '../lib/api'
import { setToken } from '../lib/auth'
import { useAuth } from '../lib/useAuth'

export default function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()
  const location = useLocation()
  const { setUser } = useAuth()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const res = await api.login(email, password)
      setToken(res.token)
      const me = await api.me()
      setUser(me)
      const defaultByRole = me?.role === 'ADMIN' ? '/admin' : me?.role === 'ENSEIGNANT' ? '/enseignant' : me?.role === 'RESPONSABLE' ? '/responsable' : '/'
      const from = location.state?.from?.pathname
      const redirectTo = from && from !== '/login' ? from : defaultByRole
      navigate(redirectTo, { replace: true })
    } catch (err) {
      setError(err.message || 'Identifiants invalides')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="mx-auto max-w-md">
      <div className="mb-8 text-center">
        <h1 className="text-2xl font-semibold">Bienvenue</h1>
        <p className="text-gray-600">Connectez-vous pour accéder à l'application</p>
      </div>
      <form onSubmit={handleSubmit} className="card p-6 space-y-4">
        {error && (
          <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">
            {error}
          </div>
        )}
        <div>
          <label className="label" htmlFor="email">Email</label>
          <input
            id="email"
            type="email"
            className="input"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="votre.email@exemple.com"
            required
          />
        </div>
        <div>
          <label className="label" htmlFor="password">Mot de passe</label>
          <input
            id="password"
            type="password"
            className="input"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
            required
          />
        </div>
        <button type="submit" className="btn btn-primary w-full" disabled={loading}>
          {loading ? 'Connexion…' : 'Se connecter'}
        </button>
      </form>
      <div className="mt-6 text-center text-xs text-gray-500">
        Besoin d'un compte ? Demandez à un administrateur.
      </div>
    </div>
  )
}
