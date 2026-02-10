import React, { useState } from 'react'; // Combined into one line
import axios from "axios";

const CreatePerformerForm = () => { // Renamed to match your filename
  const [name, setName] = useState('');
  const [bio, setBio] = useState('');
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImage(file);
      setPreview(URL.createObjectURL(file));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const formData = new FormData();
    formData.append('name', name);
    formData.append('bio', bio);
    if (image) formData.append('image', image);

    try {
      await axios.post('http://localhost:8082/performers', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      alert("Performer added!");
      setName(''); setBio(''); setImage(null); setPreview(null);
    } catch (err) {
      console.error(err);
      alert("Error adding performer");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '450px' }}>
      <div className="card border-0 shadow-sm p-4">
        <div className="text-center mb-4">
          <h2 className="fw-bold">New Performer</h2>
          <p className="text-muted small">Create a profile for the artist</p>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="text-center mb-4">
            <div 
              className="mx-auto rounded-circle bg-light d-flex align-items-center justify-content-center overflow-hidden border"
              style={{ width: '120px', height: '120px', cursor: 'pointer' }}
              onClick={() => document.getElementById('fileInput').click()}
            >
              {preview ? (
                <img src={preview} alt="Preview" className="w-100 h-100 object-fit-cover" />
              ) : (
                <i className="bi bi-camera text-muted fs-2"></i>
              )}
            </div>
            <input 
              id="fileInput"
              type="file" 
              hidden 
              accept="image/*" 
              onChange={handleFileChange} 
            />
            <small className="text-primary d-block mt-2">Upload Photo</small>
          </div>

          <div className="mb-3">
            <label className="form-label small fw-bold text-uppercase">Stage Name</label>
            <input 
              className="form-control bg-light border-0" 
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="e.g. Coldplay"
              required 
            />
          </div>

          <div className="mb-4">
            <label className="form-label small fw-bold text-uppercase">Short Bio</label>
            <textarea 
              className="form-control bg-light border-0" 
              rows="3"
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              placeholder="Tell us about the artist..."
            />
          </div>

          <button 
            type="submit" 
            className="btn btn-dark w-100 rounded-pill py-2 fw-bold"
            disabled={loading}
          >
            {loading ? 'Saving...' : 'Save Performer'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreatePerformerForm; 