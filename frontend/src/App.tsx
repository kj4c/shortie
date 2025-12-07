import { useState } from 'react'
import './App.css'

/**
 * Main App Component
 * 
 * IMPLEMENTATION STEPS:
 * 1. Create tab navigation (URL / File)
 * 2. Implement URLForm component
 * 3. Implement FileForm component
 * 4. Implement ResultDisplay component
 * 5. Handle form submissions
 * 6. Display generated short URLs
 * 
 * TODO:
 * - Add loading states
 * - Add error handling
 * - Add copy-to-clipboard functionality
 * - Add QR code generation (optional)
 */

type Tab = 'url' | 'file'

interface ResourceResult {
  shortCode: string
  shortUrl: string
  type: string
}

function App() {
  const [activeTab, setActiveTab] = useState<Tab>('url')
  const [result, setResult] = useState<ResourceResult | null>(null)

  // TODO: Implement form submission handlers
  // const handleUrlSubmit = async (data: UrlFormData) => {
  //   const response = await api.createUrl(data)
  //   setResult(response)
  // }
  // 
  // const handleFileSubmit = async (data: FileFormData) => {
  //   const response = await api.createFile(data)
  //   setResult(response)
  // }

  return (
    <div className="app">
      <header className="header">
        <h1 className="logo">shortie</h1>
        <p className="tagline">Secure URL Shortener & File Drop</p>
      </header>

      <main className="main">
        {/* Tab Navigation */}
        <nav className="tabs">
          <button
            className={`tab ${activeTab === 'url' ? 'active' : ''}`}
            onClick={() => setActiveTab('url')}
          >
            Shorten URL
          </button>
          <button
            className={`tab ${activeTab === 'file' ? 'active' : ''}`}
            onClick={() => setActiveTab('file')}
          >
            Upload File
          </button>
        </nav>

        {/* Form Container */}
        <div className="form-container">
          {activeTab === 'url' ? (
            <UrlForm onResult={setResult} />
          ) : (
            <FileForm onResult={setResult} />
          )}
        </div>

        {/* Result Display */}
        {result && (
          <div className="result">
            <h3>Your short link is ready!</h3>
            <div className="short-url">
              <code>{window.location.origin}/s/{result.shortCode}</code>
              <button 
                className="copy-btn"
                onClick={() => {
                  // TODO: Implement copy to clipboard
                  navigator.clipboard.writeText(
                    `${window.location.origin}/s/${result.shortCode}`
                  )
                }}
              >
                Copy
              </button>
            </div>
          </div>
        )}
      </main>

      <footer className="footer">
        <p>Built with Kotlin + Spring Boot + React</p>
      </footer>
    </div>
  )
}

/**
 * URL Shortener Form
 * 
 * TODO: Implement
 * - URL input field with validation
 * - Optional expiration time (hours)
 * - Optional max visits limit
 * - Submit button
 */
interface FormProps {
  onResult: (result: ResourceResult) => void
}

function UrlForm({ onResult }: FormProps) {
  const [url, setUrl] = useState('')
  const [maxVisits, setMaxVisits] = useState('')
  const [expiresInHours, setExpiresInHours] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)

    // TODO: Implement API call
    // try {
    //   const response = await fetch('/api/resources/url', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify({
    //       url,
    //       maxVisits: maxVisits ? parseInt(maxVisits) : null,
    //       expiresInHours: expiresInHours ? parseInt(expiresInHours) : null,
    //     }),
    //   })
    //   const data = await response.json()
    //   onResult(data)
    // } catch (error) {
    //   console.error('Failed to shorten URL:', error)
    // } finally {
    //   setLoading(false)
    // }

    // Placeholder response
    setTimeout(() => {
      onResult({
        shortCode: 'abc123',
        shortUrl: '/s/abc123',
        type: 'URL',
      })
      setLoading(false)
    }, 500)
  }

  return (
    <form className="form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="url">URL to shorten</label>
        <input
          type="url"
          id="url"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="https://example.com/very/long/url..."
          required
        />
      </div>

      <div className="form-row">
        <div className="form-group">
          <label htmlFor="maxVisits">Max visits (optional)</label>
          <input
            type="number"
            id="maxVisits"
            value={maxVisits}
            onChange={(e) => setMaxVisits(e.target.value)}
            placeholder="e.g., 100"
            min="1"
          />
        </div>

        <div className="form-group">
          <label htmlFor="expiresInHours">Expires in hours (optional)</label>
          <input
            type="number"
            id="expiresInHours"
            value={expiresInHours}
            onChange={(e) => setExpiresInHours(e.target.value)}
            placeholder="e.g., 24"
            min="1"
          />
        </div>
      </div>

      <button type="submit" className="submit-btn" disabled={loading}>
        {loading ? 'Creating...' : 'Shorten URL'}
      </button>
    </form>
  )
}

/**
 * File Upload Form
 * 
 * TODO: Implement
 * - File input with drag & drop
 * - Show selected file info
 * - Optional expiration time
 * - Optional max downloads limit
 * - Submit button with upload progress
 */
function FileForm({ onResult }: FormProps) {
  const [file, setFile] = useState<File | null>(null)
  const [maxVisits, setMaxVisits] = useState('')
  const [expiresInHours, setExpiresInHours] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!file) return
    setLoading(true)

    // TODO: Implement API call with FormData
    // try {
    //   const formData = new FormData()
    //   formData.append('file', file)
    //   if (maxVisits) formData.append('maxVisits', maxVisits)
    //   if (expiresInHours) formData.append('expiresInHours', expiresInHours)
    //   
    //   const response = await fetch('/api/resources/file', {
    //     method: 'POST',
    //     body: formData,
    //   })
    //   const data = await response.json()
    //   onResult(data)
    // } catch (error) {
    //   console.error('Failed to upload file:', error)
    // } finally {
    //   setLoading(false)
    // }

    // Placeholder response
    setTimeout(() => {
      onResult({
        shortCode: 'xyz789',
        shortUrl: '/s/xyz789',
        type: 'FILE',
      })
      setLoading(false)
    }, 500)
  }

  return (
    <form className="form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="file">File to upload</label>
        <div className="file-input-wrapper">
          <input
            type="file"
            id="file"
            onChange={(e) => setFile(e.target.files?.[0] || null)}
            required
          />
          <div className="file-input-display">
            {file ? (
              <span className="file-name">{file.name}</span>
            ) : (
              <span className="file-placeholder">
                Click or drag to select a file
              </span>
            )}
          </div>
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label htmlFor="maxDownloads">Max downloads (optional)</label>
          <input
            type="number"
            id="maxDownloads"
            value={maxVisits}
            onChange={(e) => setMaxVisits(e.target.value)}
            placeholder="e.g., 10"
            min="1"
          />
        </div>

        <div className="form-group">
          <label htmlFor="fileExpiresInHours">Expires in hours (optional)</label>
          <input
            type="number"
            id="fileExpiresInHours"
            value={expiresInHours}
            onChange={(e) => setExpiresInHours(e.target.value)}
            placeholder="e.g., 24"
            min="1"
          />
        </div>
      </div>

      <button type="submit" className="submit-btn" disabled={loading || !file}>
        {loading ? 'Uploading...' : 'Upload File'}
      </button>
    </form>
  )
}

export default App

