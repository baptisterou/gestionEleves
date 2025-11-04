export function filenameFromContentDisposition(header, fallback = 'download.pdf') {
  if (!header) return fallback
  const filenameStar = /filename\*=(?:UTF-8''|)([^;]+)/i.exec(header)
  if (filenameStar && filenameStar[1]) {
    try {
      return decodeURIComponent(filenameStar[1].replace(/"/g, '').trim())
    } catch (_) {}
  }
  const filenameBasic = /filename="?([^";]+)"?/i.exec(header)
  if (filenameBasic && filenameBasic[1]) return filenameBasic[1].trim()
  return fallback
}

export function saveBlob(blob, filename) {
  const url = window.URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = filename
  document.body.appendChild(a)
  a.click()
  a.remove()
  window.URL.revokeObjectURL(url)
}
