import React, { useState } from 'react'
import { api } from '../../lib/api'
import { filenameFromContentDisposition, saveBlob } from '../../lib/download'

export default function BulletinExport() {
  const [eleveId, setEleveId] = useState('')
  const y = new Date().getFullYear()
  const [annee, setAnnee] = useState(`${y}/${y + 1}`)
  const [trimestre, setTrimestre] = useState('T1')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [info, setInfo] = useState('')

  const handleExport = async (e) => {
    e.preventDefault()
    setError('')
    setInfo('')
    if (!eleveId) {
      setError("Veuillez renseigner l'identifiant de l'élève")
      return
    }
    setLoading(true)
    try {
      const { blob, contentDisposition } = await api.exportBulletin({ eleveId, trimestre, annee })
      const filename = filenameFromContentDisposition(
        contentDisposition,
        `bulletin_${eleveId}_${trimestre}_${annee}.pdf`
      )
      saveBlob(blob, filename)
      setInfo('Export réussi. Le téléchargement va commencer...')
    } catch (e) {
      setError(e.message || 'Erreur lors de lexport du bulletin')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <h1 className="text-xl font-semibold">Exporter un bulletin (PDF)</h1>
      <form onSubmit={handleExport} className="card p-6 space-y-4 max-w-xl">
        {error && (
          <div className="rounded-md border border-red-200 bg-red-50 p-3 text-sm text-red-700">{error}</div>
        )}
        {info && (
          <div className="rounded-md border border-green-200 bg-green-50 p-3 text-sm text-green-700">{info}</div>
        )}
        <div>
          <label className="label" htmlFor="eleveId">Identifiant élève</label>
          <input
            id="eleveId"
            type="text"
            className="input"
            placeholder="ex: 42"
            value={eleveId}
            onChange={(e) => setEleveId(e.target.value)}
            required
          />
        </div>
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
          <div>
            <label className="label" htmlFor="annee">Année scolaire</label>
            <input
              id="annee"
              type="text"
              className="input"
              placeholder="2024"
              value={annee}
              onChange={(e) => setAnnee(e.target.value)}
              required
            />
          </div>
          <div>
            <label className="label" htmlFor="trimestre">Trimestre</label>
            <select id="trimestre" className="input" value={trimestre} onChange={(e) => setTrimestre(e.target.value)}>
              <option value="T1">T1</option>
              <option value="T2">T2</option>
              <option value="T3">T3</option>
            </select>
          </div>
        </div>
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Export en cours…' : 'Exporter le bulletin'}
        </button>
      </form>
      <p className="text-xs text-gray-500">Astuce: si votre API propose une recherche d'élèves, cette page pourra qfficher une liste avec auto-complétion.</p>
    </div>
  )
}
