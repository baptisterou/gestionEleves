import React, { useState } from 'react'
import { ChevronDown, ChevronRight, Users, GraduationCap, BookOpen, FileText, School } from 'lucide-react'

export default function AdminToolbar({ 
  category, 
  setCategory
}) {
  const [open, setOpen] = useState(false)

  const categories = [
    { name: 'Utilisateurs', icon: <Users size={16} /> },
    { name: 'Eleves', icon: <GraduationCap size={16} /> },
    { name: 'Matieres', icon: <BookOpen size={16} /> },
    { name: 'Classes', icon: <School size={16} /> },
    { name: 'Inscriptions', icon: <FileText size={16} /> }
  ]

  return (
    <div className="relative">
      {/* Menu dépliable de catégories */}
      <div className="bg-white border rounded-md p-2 card w-fit relative">
        <button
          onClick={() => setOpen(!open)}
          className="flex items-center justify-between gap-2 rounded-md px-3 py-1 text-sm font-semibold hover:bg-gray-50 w-full"
        >
          <span>Catégorie</span>
          {open ? <ChevronDown size={14} /> : <ChevronRight size={14} />}
        </button>

        {/* Menu déroulant en position absolue */}
        {open && (
          <div className="absolute top-full left-0 mt-1 w-48 bg-white border border-gray-200 rounded-md shadow-lg z-20">
            <div className="py-1">
              {categories.map((cat) => (
                <button
                  key={cat.name}
                  onClick={() => {
                    setCategory(cat.name)
                    setOpen(false)
                  }}
                  className={`flex items-center gap-2 w-full px-3 py-2 text-sm text-left ${
                    category === cat.name 
                      ? 'bg-gray-100 font-medium' 
                      : 'hover:bg-gray-50'
                  }`}
                >
                  <span className="text-gray-500">{cat.icon}</span>
                  <span>{cat.name}</span>
                </button>
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Overlay pour fermer en cliquant à l'extérieur */}
      {open && (
        <div 
          className="fixed inset-0 z-10"
          onClick={() => setOpen(false)}
        />
      )}
    </div>
  )
}