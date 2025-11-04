import React from 'react'

export default function GuardianHome() {
  return (
    <div className="space-y-4">
      <h1 className="text-xl font-semibold">Espace Responsable</h1>
      <div className="card p-4 space-y-2">
        <p className="text-sm text-gray-700">Gérez les informations de votre/vos élève(s) et exportez les bulletins par trimestre.</p>
        <div>
          <a href="/responsable/bulletins" className="btn btn-primary">Exporter un bulletin</a>
        </div>
      </div>
    </div>
  )
}
