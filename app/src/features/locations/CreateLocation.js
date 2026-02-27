import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ErrorModal from '../../components/modals/ErrorModal';
import LocationDataGenerator from '../../utils/LocationDataGenerator';
import { ROUTES } from '../../constants/routes';
import axiosClient from '../../api/axiosClient';
import { useNavigate } from 'react-router-dom';

const CreateLocation = () => {
    const navigate = useNavigate();
    const [locationData, setLocationData] = useState({
        name: '',
        description: ''
    });

    const [image, setImage] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);

    const [errorState, setErrorState] = useState({ show: false, title: '', messages: [] });

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (!file) return;
        setImage(file);
        setImagePreview(URL.createObjectURL(file));
    };

    const removeImage = () => {
        setImage(null);
        setImagePreview(null);
    };

    const handleSubmit = async () => {
        try {
            const form = new FormData();
            form.append('name', locationData.name);
            form.append('description', locationData.description);
            if (image) form.append('imageUrl', image);

            const locRes = await axiosClient.post(`${ROUTES.LOCATIONS.ADMIN_CREATE}`, form, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });

            alert("Location created and all events scheduled!");
            navigate(ROUTES.LOCATIONS.DETAILS(locRes.data.id));
        } catch (err) {
            handleError(err);
        }
    };

    const fillTestData = () => {
        const generated = LocationDataGenerator.generate();
        setLocationData({ name: generated.name, description: generated.description });
    };

    const handleError = (err) => {
        const messages = err.response?.data ? Object.values(err.response.data) : ["Submission failed"];
        setErrorState({ show: true, title: "Error", messages });
    };

    return (
        <div style={styles.page}>
            <div style={styles.container}>
                <header style={styles.header}>
                    <h1 style={styles.title}>Create Location</h1>
                </header>

                <form style={styles.formCard}>
                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>Location Details</h3>

                        <div style={styles.inputGroup}>
                            <label style={styles.label}>Location Name</label>
                            <input
                                type="text"
                                placeholder="e.g. Madison Square Garden"
                                style={styles.input}
                                value={locationData.name}
                                onChange={(e) => setLocationData({ ...locationData, name: e.target.value })}
                            />
                        </div>

                        <div style={styles.inputGroup}>
                            <label style={styles.label}>Location Description</label>
                            <textarea
                                placeholder="Describe the venue — capacity, facilities, address..."
                                style={styles.textarea}
                                rows={4}
                                value={locationData.description}
                                onChange={(e) => setLocationData({ ...locationData, description: e.target.value })}
                            />
                        </div>

                        <div style={styles.inputGroup}>
                            <label style={styles.label}>Location Image</label>
                            {imagePreview ? (
                                <div style={styles.previewWrapper}>
                                    <img src={imagePreview} alt="Preview" style={styles.previewImg} />
                                    <button type="button" onClick={removeImage} style={styles.removeImgBtn}>
                                        ✕ Remove
                                    </button>
                                </div>
                            ) : (
                                <label style={styles.uploadArea}>
                                    <input
                                        type="file"
                                        accept="image/*"
                                        style={{ display: 'none' }}
                                        onChange={handleImageChange}
                                    />
                                    <span style={styles.uploadIcon}>🖼️</span>
                                    <span style={styles.uploadText}>Click to upload an image</span>
                                    <span style={styles.uploadHint}>PNG, JPG, WEBP up to 10MB</span>
                                </label>
                            )}
                        </div>
                    </section>

                    <button type="button" style={styles.submitBtn} onClick={handleSubmit}>
                        Save Location
                    </button>
                </form>
            </div>

            <ErrorModal
                show={errorState.show}
                title={errorState.title}
                messages={errorState.messages}
                onClose={() => setErrorState({ ...errorState, show: false })}
            />
        </div>
    );
};

const styles = {
    page: { backgroundColor: '#f4f7fa', minHeight: '100vh', padding: '60px 20px', fontFamily: '"Inter", sans-serif' },
    container: { maxWidth: '750px', margin: '0 auto' },
    header: { marginBottom: '40px', textAlign: 'center' },
    title: { fontSize: '32px', fontWeight: '800', color: '#1e293b' },
    formCard: { backgroundColor: '#fff', padding: '40px', borderRadius: '24px', boxShadow: '0 20px 25px -5px rgba(0,0,0,0.05)' },
    section: { marginBottom: '30px', borderBottom: '1px solid #f1f5f9', paddingBottom: '20px' },
    sectionTitle: { fontSize: '16px', fontWeight: '700', color: '#2563eb', marginBottom: '15px' },
    inputGroup: { marginBottom: '20px' },
    label: { display: 'block', fontWeight: '600', marginBottom: '8px', fontSize: '14px', color: '#374151' },
    miniLabel: { display: 'block', fontWeight: '600', marginBottom: '4px', fontSize: '12px', color: '#64748b' },
    input: { width: '100%', padding: '12px', borderRadius: '10px', border: '1px solid #e2e8f0', boxSizing: 'border-box' },
    textarea: { width: '100%', padding: '12px', borderRadius: '10px', border: '1px solid #e2e8f0', boxSizing: 'border-box', resize: 'vertical', fontFamily: 'inherit', fontSize: '14px' },
    uploadArea: { display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: '6px', border: '2px dashed #cbd5e1', borderRadius: '12px', padding: '32px', cursor: 'pointer', backgroundColor: '#f8fafc', transition: 'background 0.2s' },
    uploadIcon: { fontSize: '32px' },
    uploadText: { fontWeight: '600', fontSize: '14px', color: '#374151' },
    uploadHint: { fontSize: '12px', color: '#94a3b8' },
    previewWrapper: { position: 'relative', display: 'inline-block' },
    previewImg: { width: '100%', maxHeight: '220px', objectFit: 'cover', borderRadius: '12px', border: '1px solid #e2e8f0' },
    removeImgBtn: { marginTop: '8px', background: 'none', border: '1px solid #e2e8f0', borderRadius: '8px', padding: '6px 12px', cursor: 'pointer', fontSize: '13px', color: '#ef4444' },
    locationRow: { display: 'flex', gap: '12px', alignItems: 'flex-end', marginBottom: '12px' },
    addBtn: { background: 'none', border: '1px dashed #2563eb', color: '#2563eb', padding: '10px 16px', borderRadius: '10px', cursor: 'pointer', fontWeight: '600', fontSize: '14px' },
    removeBtn: { background: 'none', border: '1px solid #fca5a5', color: '#ef4444', borderRadius: '8px', padding: '8px 12px', cursor: 'pointer', fontWeight: '700', fontSize: '16px', flexShrink: 0 },
    submitBtn: { width: '100%', backgroundColor: '#2563eb', color: '#fff', padding: '16px', borderRadius: '12px', fontWeight: '700', border: 'none', cursor: 'pointer', fontSize: '16px' },
    fillBtn: { marginTop: '12px', background: 'none', border: '1px dashed #94a3b8', color: '#64748b', padding: '8px 18px', borderRadius: '20px', cursor: 'pointer', fontSize: '13px', fontWeight: '600' },
    grid2: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' },
};

export default CreateLocation;
