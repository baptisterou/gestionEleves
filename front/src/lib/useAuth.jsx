import React, { createContext, useContext, useEffect, useMemo, useState } from 'react'
import { api } from './api'
import { clearToken, getToken, setToken } from './auth'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    let mounted = true
    async function load() {
      setLoading(true)
      setError('')
      try {
        if (!getToken()) {
          setUser(null)
          return
        }
        const me = await api.me()
        if (mounted) setUser(me)
      } catch (e) {
        console.error(e)
        clearToken()
        if (mounted) setUser(null)
      } finally {
        if (mounted) setLoading(false)
      }
    }
    load()
    return () => {
      mounted = false
    }
  }, [])

  const value = useMemo(() => ({ user, setUser, loading, error, setError, setToken }), [user, loading, error])

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}
