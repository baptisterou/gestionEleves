/*
 * Composant principal de l'application
 * Définit toutes les routes et la structure de navigation
 * Gère l'authentification et les autorisations par rôle
 */
import React from 'react'
import { Routes, Route, Navigate, useLocation, Outlet } from 'react-router-dom'

// Pages
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import AdminHome from './pages/admin/AdminHome'
import UsersList from './pages/admin/UsersList'
import ElevesList from './pages/admin/ElevesList'
import ClassesList from './pages/admin/ClassesList'
import MatieresList from './pages/admin/MatieresList'
import AdminPlaceholder from './pages/admin/AdminPlaceholder'
import Inscriptions from './pages/admin/Inscriptions'
import TeacherHome from './pages/enseignant/TeacherHome'
import TeacherNotes from './pages/enseignant/TeacherNotes'
import GuardianHome from './pages/responsable/GuardianHome'
import BulletinExport from './pages/responsable/BulletinExport'
import GuardianNotes from './pages/responsable/GuardianNotes'
import AdminStats from './pages/admin/AdminStats'

// Composants
import Navbar from './components/Navbar'
import RoleRoute from './components/RoleRoute'

// Layouts
import AdminLayout from './layouts/AdminLayout'
import TeacherLayout from './layouts/TeacherLayout'
import GuardianLayout from './layouts/GuardianLayout'

// Services et hooks
import { isAuthenticated } from './lib/auth'
import { AuthProvider, useAuth } from './lib/useAuth'
import { ToastProvider } from './lib/useToast'

/*
 * Composant pour protéger les routes nécessitant une authentification
 * Redirige vers la page de connexion si l'utilisateur n'est pas authentifié
 */
function ProtectedRoute({ children }) {
  const location = useLocation()
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace state={{ from: location }} />
  }
  return children
}

/*
 * Structure principale de l'application
 * Affiche la barre de navigation si l'utilisateur est authentifié
 * Définit toutes les routes de l'application
 */
function AppShell() {
  const authed = isAuthenticated()
  return (
    <div className="min-h-screen">
      {/* Barre de navigation affichée uniquement si l'utilisateur est connecté */}
      {authed && <Navbar />}
      <main className="mx-auto max-w-6xl px-4 py-8">
        <Routes>
          {/* Route de connexion */}
          <Route path="/login" element={<Login />} />

          {/* Route du tableau de bord */}
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          {/* Routes pour les administrateurs */}
          <Route
            path="/admin"
            element={
              <ProtectedRoute>
                <RoleRoute allow={["ADMIN"]}>
                  <Outlet />
                </RoleRoute>
              </ProtectedRoute>
            }
          >
            <Route index element={<AdminHome />} />
            <Route path="utilisateurs" element={<UsersList />} />
            <Route path="eleves" element={<ElevesList />} />
            <Route path="classes" element={<ClassesList />} />
            <Route path="matieres" element={<MatieresList />} />
            <Route path="inscriptions" element={<Inscriptions />} />
            <Route path="stats" element={<AdminStats />} />
          </Route>

          {/* Routes pour les enseignants */}
          <Route
            path="/enseignant"
            element={
              <ProtectedRoute>
                <RoleRoute allow={["ENSEIGNANT"]}>
                  <TeacherLayout />
                </RoleRoute>
              </ProtectedRoute>
            }
          >
            <Route index element={<TeacherHome />} />
            <Route path="notes" element={<TeacherNotes />} />
          </Route>

          {/* Routes pour les responsables légaux */}
          <Route
            path="/responsable"
            element={
              <ProtectedRoute>
                <RoleRoute allow={["RESPONSABLE"]}>
                  <GuardianLayout />
                </RoleRoute>
              </ProtectedRoute>
            }
          >
            <Route index element={<GuardianHome />} />
            <Route path="notes" element={<GuardianNotes />} />
            <Route path="bulletins" element={<BulletinExport />} />
          </Route>

          {/* Route par défaut - redirige selon l'état d'authentification */}
          <Route path="*" element={<Navigate to={authed ? '/' : '/login'} replace />} />
        </Routes>
      </main>
    </div>
  )
}

/*
 * Composant racine de l'application
 * Initialise les providers pour l'authentification et les notifications
 */
export default function App() {
  return (
    <ToastProvider>
      <AuthProvider>
        <AppShell />
      </AuthProvider>
    </ToastProvider>
  )
}
