import React, { createContext, useCallback, useContext, useMemo, useState } from 'react'

const ToastContext = createContext(null)

export function ToastProvider({ children }) {
  const [toasts, setToasts] = useState([])

  const remove = useCallback((id) => {
    setToasts((prev) => prev.filter((t) => t.id !== id))
  }, [])

  const show = useCallback((message, { type = 'info', timeout = 3000 } = {}) => {
    const id = Math.random().toString(36).slice(2)
    setToasts((prev) => [...prev, { id, message, type }])
    if (timeout) {
      setTimeout(() => remove(id), timeout)
    }
    return id
  }, [remove])

  const value = useMemo(() => ({ show, remove }), [show, remove])

  return (
    <ToastContext.Provider value={value}>
      {children}
      <div className="pointer-events-none fixed bottom-4 right-4 z-50 flex w-full max-w-sm flex-col gap-2">
        {toasts.map((t) => (
          <div key={t.id} className={`pointer-events-auto rounded-md border p-3 text-sm shadow-sm ${t.type === 'error' ? 'border-red-200 bg-red-50 text-red-800' : t.type === 'success' ? 'border-green-200 bg-green-50 text-green-800' : 'border-gray-200 bg-white text-gray-800'}`}>
            {t.message}
          </div>
        ))}
      </div>
    </ToastContext.Provider>
  )
}

export function useToast() {
  const ctx = useContext(ToastContext)
  if (!ctx) throw new Error('useToast must be used within ToastProvider')
  return ctx
}
