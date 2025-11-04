import React from 'react'
import { NavLink, Outlet } from 'react-router-dom'

export default function GuardianLayout() {
  return (
    <div className="grid gap-6 md:grid-cols-12">
      <aside className="md:col-span-3 lg:col-span-2">
        <nav className="card p-4 space-y-2">
          <Section title="Responsable" />
          <MenuLink to="/responsable" label="Accueil" />
          <MenuLink to="/responsable/notes" label="Notes" />
          <MenuLink to="/responsable/bulletins" label="Bulletins (export)" />
        </nav>
      </aside>
      <section className="md:col-span-9 lg:col-span-10">
        <Outlet />
      </section>
    </div>
  )
}

function Section({ title }) {
  return <div className="mb-2 text-xs font-semibold uppercase tracking-wide text-gray-500">{title}</div>
}

function MenuLink({ to, label }) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) =>
        `block rounded-md px-3 py-2 text-sm ${isActive ? 'bg-gray-100 font-medium' : 'hover:bg-gray-50'}`
      }
    >
      {label}
    </NavLink>
  )
}
