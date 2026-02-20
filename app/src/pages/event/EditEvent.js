import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const EditEvent = () => {
    const { id } = useParams(); // Get the event ID from the URL
    const navigate = useNavigate();

    const [eventData, setEventData] = useState({
        eventName: '',
        description: '',
        artistName: '',
        categories: [],
        eventLocations: [{ locationId: '', eventDate: '', tickets: 1, price: 0 }]
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

    // 1. Fetch Master Data (Locations, Artists, Categories)
    useEffect(() => {
        const fetchMasterData = async () => {
            try {
                const [locRes, catRes] = await Promise.all([
                    axios.get(`http://localhost:8082/locations`),
                    axios.get(`http://localhost:8082/categories`)
                ]);
                setLocations(locRes.data);
                setCategories(catRes.data);
            } catch (err) {
                console.error("Error fetching master data:", err);
            }
        };
        fetchMasterData();
    }, []);

    // 2. Preload Existing Event Data
    useEffect(() => {
        if (id) {
            axios.get(`http://localhost:8082/events/${id}`)
                .then(res => {
                    const data = res.data;
                    console.log(res.data);
                    console.log("Mapping data for form:", data);

                    setEventData({
                        eventName: data.name,
                        description: data.description,
                        categories: data.categories ? data.categories.map(c => c.id) : [],
                        eventLocations: data.eventLocations.map(loc => ({
                            eventLocationId: loc.eventLocationId,
                            locationId: loc.locationId, // Keep this for the value
                            eventDate: loc.eventStartTime ? loc.eventStartTime.substring(0, 16) : '', // Changed from date to eventDate
                            tickets: loc.ticketsCount,
                            price: loc.pricePerTicket
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
            categories: prev.categories.includes(catId)
                ? prev.categories.filter(id => id !== catId)
                : [...prev.categories, catId]
        }));
    };

    const updateLocation = (index, field, value) => {
        const newLocations = [...eventData.eventLocations];
        newLocations[index][field] = (field === 'price' || field === 'tickets' || field === 'locationId')
            ? Number(value) : value;
        setEventData({ ...eventData, eventLocations: newLocations });
    };

    const addLocation = () => {
        setEventData({
            ...eventData,
            eventLocations: [...eventData.eventLocations, {  eventLocationId: '', locationId: '', eventDate: '', tickets: 1, price: 0 }]
        });
    };

    const handleUpdate = () => {
        // Send as PUT request for editing
        console.log(eventData);
        axios.put(`http://localhost:8082/events/${id}`, eventData)
            .then(() => {
                alert("Event updated successfully!");
                navigate(`/events/${id}`);
            })
            .catch(err => {
                console.error("Update failed:", err);
                alert("Failed to update event.");
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
                                value={eventData.description}
                                onChange={(e) => setEventData({ ...eventData, description: e.target.value })}
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
                                            style={eventData.categories.includes(cat.id) ? styles.pillActive : styles.pill}
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
                                        value={loc.eventDate} // This matches the key in state now
                                        onChange={(e) => updateLocation(index, 'eventDate', e.target.value)}
                                    />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Tickets</label>
                                    <input
                                        type="number"
                                        style={styles.input}
                                        value={loc.tickets}
                                        onChange={(e) => updateLocation(index, 'tickets', e.target.value)}
                                    />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Price (BGN)</label>
                                    <input
                                        type="number"
                                        style={styles.input}
                                        value={loc.price}
                                        onChange={(e) => updateLocation(index, 'price', e.target.value)}
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