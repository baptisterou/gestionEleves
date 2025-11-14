import React, { useState } from 'react'
import { NavLink, Outlet } from 'react-router-dom'
import { ChevronDown, ChevronRight, Users, Home, BookOpen, GraduationCap, BarChart } from 'lucide-react'

export default function AdminLayout() {
  const [open, setOpen] = useState(false)

  return (
    <div className="grid gap-2 md:grid-cols-12">
      {/* Sidebar */}
      <aside className="md:col-span-3 lg:col-span-2">
        <nav className="card p-2 space-y-2">
          <button
            onClick={() => setOpen(!open)}
            className="flex items-center justify-between w-full text-left rounded-md px-3 py-2 text-sm font-semibold hover:bg-gray-50"
          >
            <span>Catégorie</span>
            {open ? <ChevronDown size={16} /> : <ChevronRight size={16} />}
          </button>

          {open && (
            <div className="mt-2 space-y-1 pl-2 border-l border-gray-200">
              <MenuLink to="/admin/utilisateurs" label="Utilisateurs" icon={<Users size={16} />} />
              <MenuLink to="/admin/eleves" label="Élèves" icon={<GraduationCap size={16} />} />
              <MenuLink to="/admin/matieres" label="Matières" icon={<BookOpen size={16} />} />
              <MenuLink to="/admin/stats" label="Statistiques" icon={<BarChart size={16} />} />
            </div>
          )}
        </nav>
      </aside>

      {/* Contenu principal */}
      <section className="md:col-span-9 lg:col-span-10">
        <Outlet />
      </section>
    </div>
  )
}

function MenuLink({ to, label, icon }) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) =>
        `flex items-center gap-2 rounded-md px-3 py-2 text-sm ${
          isActive ? 'bg-gray-100 font-medium' : 'hover:bg-gray-50'
        }`
      }
    >
      {icon && <span className="text-gray-500">{icon}</span>}
      <span>{label}</span>
    </NavLink>
  )
}
