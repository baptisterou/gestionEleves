import { useEffect, useState } from 'react'

export function useFetch(asyncFn, deps = []) {
  const [data, setData] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    let mounted = true
    setLoading(true)
    setError('')
    ;(async () => {
      try {
        const res = await asyncFn()
        if (mounted) setData(res)
      } catch (e) {
        if (mounted) setError(e.message || 'Erreur')
      } finally {
        if (mounted) setLoading(false)
      }
    })()
    return () => {
      mounted = false
    }
  }, deps)

  return { data, error, loading, setData, setError, setLoading }
}
