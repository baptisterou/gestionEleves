import React from 'react'
import { Routes, Route, Navigate, useLocation, Outlet } from 'react-router-dom'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Navbar from './components/Navbar'
import { isAuthenticated } from './lib/auth'
import { AuthProvider, useAuth } from './lib/useAuth'
import { ToastProvider } from './lib/useToast'
import RoleRoute from './components/RoleRoute'
import AdminLayout from './layouts/AdminLayout'
import TeacherLayout from './layouts/TeacherLayout'
import GuardianLayout from './layouts/GuardianLayout'
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

function ProtectedRoute({ children }) {
  const location = useLocation()
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace state={{ from: location }} />
  }
  return children
}

function AppShell() {
  const authed = isAuthenticated()
  return (
    <div className="min-h-screen">
      {authed && <Navbar />}
      <main className="mx-auto max-w-6xl px-4 py-8">
        <Routes>
          <Route path="/login" element={<Login />} />

          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />

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

          <Route path="*" element={<Navigate to={authed ? '/' : '/login'} replace />} />
        </Routes>
      </main>
    </div>
  )
}

export default function App() {
  return (
    <ToastProvider>
      <AuthProvider>
        <AppShell />
      </AuthProvider>
    </ToastProvider>
  )
}
