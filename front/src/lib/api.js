import { getToken, clearToken } from './auth'

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'
const EXPORT_TIMEOUT = Number(import.meta.env.VITE_EXPORT_TIMEOUT_MS || 30000)
const BULLETIN_EXPORT_PATH = import.meta.env.VITE_BULLETIN_EXPORT_PATH || '/api/bulletin/export'

async function request(path, { method = 'GET', body, headers = {}, signal } = {}) {
  const token = getToken()
  const res = await fetch(`${BASE_URL}${path}` + (method === 'GET' && body ? `?${new URLSearchParams(body)}` : ''), {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...headers,
    },
    body: method !== 'GET' && body ? JSON.stringify(body) : undefined,
    signal,
  })

  if (res.status === 401) {
    clearToken()
  }

  const text = await res.text()
  const data = text ? JSON.parse(text) : null
  if (!res.ok) {
    const message = data?.message || data?.error || res.statusText
    throw new Error(message)
  }
  return data
}

async function requestBlob(path, { method = 'GET', params, headers = {}, timeoutMs = EXPORT_TIMEOUT } = {}) {
  const token = getToken()
  const controller = new AbortController()
  const id = setTimeout(() => controller.abort(), timeoutMs)
  const url = `${BASE_URL}${path}` + (params ? `?${new URLSearchParams(params).toString()}` : '')
  const res = await fetch(url, {
    method,
    headers: {
      Accept: 'application/pdf,application/octet-stream',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...headers,
    },
    signal: controller.signal,
  })
  clearTimeout(id)

  if (res.status === 401) {
    clearToken()
  }
  if (!res.ok) {

    try {
      const text = await res.text()
      const data = text ? JSON.parse(text) : null
      const message = data?.message || data?.error || res.statusText
      throw new Error(message)
    } catch (_e) {
      throw new Error(res.statusText)
    }
  }

  const blob = await res.blob()
  const cd = res.headers.get('Content-Disposition') || ''
  return { blob, contentDisposition: cd }
}

export const api = {

  login: (email, password) => request('/auth/login', { method: 'POST', body: { email, password } }),
  me: () => request('/users/me'),
  
  
  listUsers: (params) => request('/api/utilisateur', { method: 'GET', body: params }),
  getUser: (id) => request(`/api/utilisateur/${id}`),
  // Création générique (public/standard) – ne permet pas de définir le rôle
  createUser: (payload) => request('/api/utilisateur', { method: 'POST', body: payload }),
  // Création ADMIN – permet de définir le rôle explicitement
  createUserAdmin: (payload) => request('/api/utilisateur/admin', { method: 'POST', body: payload }),
  // Mise à jour générique – ne change pas le rôle
  updateUser: (id, payload) => request(`/api/utilisateur/${id}`, { method: 'PUT', body: payload }),
  // Changement de rôle (ADMIN uniquement)
  updateUserRole: (id, payload) => request(`/api/utilisateur/${id}/role`, { method: 'PUT', body: payload }),
  deleteUser: (id) => request(`/api/utilisateur/${id}`, { method: 'DELETE' }),

  listEleves: (params) => request('/api/eleve', { method: 'GET', body: params }),
  getEleve: (id) => request(`/api/eleve/${id}`),
  createEleve: (payload) => request('/api/eleve', { method: 'POST', body: payload }),
  updateEleve: (id, payload) => request(`/api/eleve/${id}`, { method: 'PUT', body: payload }),
  deleteEleve: (id) => request(`/api/eleve/${id}`, { method: 'DELETE' }),

  listClasses: (params) => request('/api/classe', { method: 'GET', body: params }),
  getClasse: (id) => request(`/api/classe/${id}`),
  createClasse: (payload) => request('/api/classe', { method: 'POST', body: payload }),
  updateClasse: (id, payload) => request(`/api/classe/${id}`, { method: 'PUT', body: payload }),
  deleteClasse: (id) => request(`/api/classe/${id}`, { method: 'DELETE' }),

  listMatieres: (params) => request('/api/matiere', { method: 'GET', body: params }),
  getMatiere: (id) => request(`/api/matiere/${id}`),
  createMatiere: (payload) => request('/api/matiere', { method: 'POST', body: payload }),
  updateMatiere: (id, payload) => request(`/api/matiere/${id}`, { method: 'PUT', body: payload }),
  deleteMatiere: (id) => request(`/api/matiere/${id}`, { method: 'DELETE' }),

  listInscriptions: (params) => request('/api/inscription', { method: 'GET', body: params }),
  createInscription: (payload) => request('/api/inscription', { method: 'POST', body: payload }),
  deleteInscription: (id) => request(`/api/inscription/${id}`, { method: 'DELETE' }),

  listNotes: (params) => request('/api/note', { method: 'GET', body: params }),
  createNote: (payload) => request('/api/note', { method: 'POST', body: payload }),
  updateNote: (id, payload) => request(`/api/note/${id}`, { method: 'PUT', body: payload }),
  deleteNote: (id) => request(`/api/note/${id}`, { method: 'DELETE' }),

  listBulletins: (params) => request('/api/bulletin', { method: 'GET', body: params }),
  createBulletin: (payload) => request('/api/bulletin', { method: 'POST', body: payload }),
  updateBulletin: (id, payload) => request(`/api/bulletin/${id}`, { method: 'PUT', body: payload }),
  deleteBulletin: (id) => request(`/api/bulletin/${id}`, { method: 'DELETE' }),

  exportBulletin: async ({ eleveId, trimestre, annee }) => {
    return requestBlob(BULLETIN_EXPORT_PATH, {
      method: 'GET',
      params: { eleveId, trimestre, annee },
    })
  },
}
