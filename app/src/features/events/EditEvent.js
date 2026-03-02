import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ErrorModal from '../../components/modals/ErrorModal';

import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';
const EditEvent = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [errorModal, setErrorModal] = useState({
        show: false,
        title: '',
        messages: []
    });

    const closeErrorModal = () => setErrorModal(prev => ({ ...prev, show: false }));
    const [eventData, setEventData] = useState({
        eventName: '',
        eventDescription: '',
        artistId: '',
        eventCategories: [],
        eventLocations: [{ eventLocationId: '', locationId: '', eventStartTime: '', ticketsCount: 1, pricePerTicket: 0 }]
    });

    const removeLocation = (index) => {
        if (eventData.eventLocations.length > 1) {
            const newLocations = eventData.eventLocations.filter((_, i) => i !== index);
            setEventData({ ...eventData, eventLocations: newLocations });
        } else {
            alert("At least one location is required.");
        }
    };

    const [locations, setLocations] = useState([]);
    const [categories, setCategories] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchMasterData = async () => {
            try {
                const [locRes, catRes] = await Promise.all([
                    axiosClient.get(`${ROUTES.LOCATIONS.BASE}`),
                    axiosClient.get(`${ROUTES.CATEGORIES.BASE}`)
                ]);
                setLocations(locRes.data);
                setCategories(catRes.data);
            } catch (err) {
                console.error("Error fetching master data:", err);
            }
        };
        fetchMasterData();
    }, []);

    useEffect(() => {
        if (id) {
            axiosClient.get(`${ROUTES.EVENTS.DETAILS(id)}`)
                .then(res => {
                    const data = res.data;
                    console.log(res.data);
                    console.log("Mapping data for form:", data);

                    setEventData({
                        eventName: data.eventName,
                        eventDescription: data.eventDescription,
                        eventCategories: data.categories ? data.categories.map(c => c.id) : [],
                        eventLocations: data.eventLocations.map(loc => ({
                            id: loc.id,
                            locationId: loc.locationId,
                            eventStartTime: loc.eventStartTime ? loc.eventStartTime.substring(0, 16) : '',
                            ticketsCount: loc.ticketsCount,
                            pricePerTicket: loc.pricePerTicket
                        }))
                    });

                    setIsLoading(false);
                })
                .catch(err => {
                    console.error("Error fetching event details:", err);
                    setIsLoading(false);
                });
        }
    }, [id]);

    const handleCategoryChange = (catId) => {
        setEventData(prev => ({
            ...prev,
            eventCategories: prev.eventCategories.includes(catId)
                ? prev.eventCategories.filter(id => id !== catId)
                : [...prev.eventCategories, catId]
        }));
    };

    const updateLocation = (index, field, value) => {
        const newLocations = [...eventData.eventLocations];
        newLocations[index][field] = (field === 'pricePerTicket' || field === 'ticketsCount' || field === 'locationId')
            ? Number(value) : value;
        setEventData({ ...eventData, eventLocations: newLocations });
    };

    const addLocation = () => {
        setEventData({
            ...eventData,
            eventLocations: [...eventData.eventLocations, { eventLocationId: '', locationId: '', eventStartTime: '', ticketsCount: 1, pricePerTicket: 0 }]
        });
    };

    const handleUpdate = () => {
        axiosClient.put(`${ROUTES.EVENTS.ADMIN_EDIT_EXISTING(id)}`, eventData)
            .then(() => {
                alert("Event updated successfully!");
                navigate(`/events/${id}`);
            })
            .catch(err => {
                if (err.response) {
                    const { status, data } = err.response;

                    if (status === 400) {
                        const errorList = data.message ? data.message.split(', ') : ['Invalid input provided.'];
                        setErrorModal({
                            show: true,
                            title: 'Validation Issues',
                            messages: errorList
                        });
                    } else if (status === 409) {
                        setErrorModal({
                            show: true,
                            title: 'Scheduling Conflict',
                            messages: Array.isArray(data.message) ? data.message : [data.message]
                        });
                    } else {
                        setErrorModal({
                            show: true,
                            title: 'Server Error',
                            messages: data.message || 'An unexpected error occurred.'
                        });
                    }
                } else {
                    setErrorModal({
                        show: true,
                        title: 'Network Error',
                        messages: 'Could not connect to the server. Please check your internet.'
                    });
                }
            });
    };

    if (isLoading) return <div style={{ textAlign: 'center', padding: '50px' }}>Loading event details...</div>;

    return (
        <div style={styles.page}>
            <div style={styles.container}>
                <header style={styles.header}>
                    <h1 style={styles.title}>Edit Event</h1>
                    <p style={styles.subtitle}>Modify the details for <strong>{eventData.eventName}</strong></p>
                </header>
                <ErrorModal
                    show={errorModal.show}
                    onClose={closeErrorModal}
                    title={errorModal.title}
                    messages={errorModal.messages}
                />
                <form style={styles.formCard}>
                    {/* SECTION 1: BASIC INFO */}
                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>1. Basic Information</h3>
                        <div style={styles.inputGroup}>
                            <label style={styles.label}>Event Title</label>
                            <input
                                type="text"
                                style={styles.input}
                                value={eventData.eventName}
                                onChange={(e) => setEventData({ ...eventData, eventName: e.target.value })}
                            />
                        </div>
                        <div style={styles.inputGroup}>
                            <label style={styles.label}>Description</label>
                            <textarea
                                style={{ ...styles.input, height: '100px', resize: 'none' }}
                                value={eventData.eventDescription}
                                onChange={(e) => setEventData({ ...eventData, eventDescription: e.target.value })}
                            />
                        </div>
                    </section>

                    {/* SECTION 2: TALENT & CATEGORY */}
                    <section style={styles.section}>
                        <div style={styles.grid2}>
                            <div style={styles.inputGroup}>
                                <label style={styles.label}>Categories</label>
                                <div style={styles.categoryPills}>
                                    {categories.map(cat => (
                                        <button
                                            key={cat.id}
                                            type="button"
                                            onClick={() => handleCategoryChange(cat.id)}
                                            style={eventData.eventCategories.includes(cat.id) ? styles.pillActive : styles.pill}
                                        >
                                            {cat.name}
                                        </button>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </section>

                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>3. Dates & Locations</h3>
                        {eventData.eventLocations.map((loc, index) => (
                            <div key={index} style={styles.locationRow}>
                                <div style={{ flex: 2 }}>
                                    <label style={styles.miniLabel}>Location</label>
                                    <select
                                        style={styles.input}
                                        value={loc.locationId} // Use locationId, not locationName
                                        onChange={(e) => updateLocation(index, 'locationId', e.target.value)} // Map to locationId
                                    >
                                        <option value="">Select Location</option>
                                        {locations.map(l => <option key={l.id} value={l.id}>{l.name}</option>)}
                                    </select>
                                </div>
                                <div style={{ flex: 2 }}>
                                    <label style={styles.miniLabel}>Date & Time</label>
                                    <input
                                        type="datetime-local"
                                        style={styles.input}
                                        value={loc.eventStartTime} // This matches the key in state now
                                        onChange={(e) => updateLocation(index, 'eventStartTime', e.target.value)}
                                    />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Tickets</label>
                                    <input
                                        type="number"
                                        style={styles.input}
                                        value={loc.ticketsCount}
                                        onChange={(e) => updateLocation(index, 'ticketsCount', e.target.value)}
                                    />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Price (BGN)</label>
                                    <input
                                        type="number"
                                        style={styles.input}
                                        value={loc.pricePerTicket}
                                        onChange={(e) => updateLocation(index, 'pricePerTicket', e.target.value)}
                                    />
                                </div>
                                <button
                                    type="button"
                                    onClick={() => removeLocation(index)}
                                    style={styles.removeBtn}
                                    title="Remove this location"
                                >
                                    ✕
                                </button>
                            </div>

                        ))}
                        <button type="button" onClick={addLocation} style={styles.addBtn}>+ Add Another Location</button>
                    </section>

                    <button
                        type="button"
                        style={styles.submitBtn}
                        onClick={handleUpdate}
                    >
                        Save Changes
                    </button>
                </form>
            </div>
        </div>
    );
};


const styles = {
    page: { backgroundColor: '#f4f7fa', minHeight: '100vh', padding: '60px 20px', fontFamily: '"Inter", sans-serif' },
    container: { maxWidth: '900px', margin: '0 auto' },
    header: { marginBottom: '40px', textAlign: 'center' },
    title: { fontSize: '36px', fontWeight: '800', color: '#1e293b', marginBottom: '10px' },
    subtitle: { color: '#64748b', fontSize: '16px' },
    formCard: { backgroundColor: '#fff', padding: '40px', borderRadius: '24px', boxShadow: '0 20px 25px -5px rgba(0,0,0,0.05)' },
    section: { marginBottom: '40px', borderBottom: '1px solid #f1f5f9', paddingBottom: '30px' },
    sectionTitle: { fontSize: '18px', fontWeight: '700', color: '#2563eb', marginBottom: '20px', textTransform: 'uppercase', letterSpacing: '1px' },
    inputGroup: { marginBottom: '20px' },
    label: { display: 'block', fontWeight: '600', color: '#334155', marginBottom: '8px', fontSize: '14px' },
    miniLabel: { display: 'block', fontWeight: '600', color: '#94a3b8', marginBottom: '5px', fontSize: '11px', textTransform: 'uppercase' },
    input: { width: '100%', padding: '12px 16px', borderRadius: '12px', border: '1px solid #e2e8f0', fontSize: '15px', outline: 'none', transition: 'border 0.2s', boxSizing: 'border-box' },
    grid2: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '30px' },
    categoryPills: { display: 'flex', flexWrap: 'wrap', gap: '10px' },
    pill: { padding: '8px 16px', borderRadius: '20px', border: '1px solid #e2e8f0', backgroundColor: '#fff', cursor: 'pointer', fontSize: '13px', color: '#64748b' },
    pillActive: { padding: '8px 16px', borderRadius: '20px', border: '1px solid #2563eb', backgroundColor: '#eff6ff', color: '#2563eb', fontWeight: '600', cursor: 'pointer', fontSize: '13px' },
    locationRow: { display: 'flex', gap: '15px', marginBottom: '20px', alignItems: 'flex-end', position: 'relative' },
    addBtn: { backgroundColor: 'transparent', border: 'none', color: '#2563eb', fontWeight: '600', cursor: 'pointer', fontSize: '14px' },
    removeBtn: { position: 'absolute', right: '-30px', bottom: '10px', backgroundColor: '#fee2e2', color: '#ef4444', border: 'none', borderRadius: '50%', width: '24px', height: '24px', cursor: 'pointer' },
    submitBtn: { width: '100%', backgroundColor: '#2563eb', color: '#fff', padding: '18px', borderRadius: '14px', fontSize: '16px', fontWeight: '700', border: 'none', cursor: 'pointer', boxShadow: '0 10px 15px -3px rgba(37, 99, 235, 0.3)', marginTop: '20px' }
};

export default EditEvent;