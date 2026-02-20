import React, { use, useEffect, useState } from 'react';
import axios from 'axios';
import ErrorModal from '../../components/system/ErrorModal';

const CreateEvent = () => {
    const [errorState, setErrorState] = useState({ show: false, title: '', messages: [] });
    const [eventData, setEventData] = useState({
        eventName: '',
        description: '',
        artistId: '',
        categories: [],
        eventLocations: [{ locationId: '', eventDate: '', tickets: 1, price: 0 }]
    });

    const handleError = (err) => {
        let title = "Submission Failed";
        let messages = ["An unexpected error occurred. Please try again."];

        if (err.response) {
            const status = err.response.status;

            if (status === 400) {
                title = "Validation Errors";
                // Convert Spring's validation map { field: message } into an array of strings
                messages = Object.values(err.response.data);
            } else if (status === 409) {
                title = "Schedule Conflict";
                messages = [err.response.data]; // The conflict message from Java
            } else if (status === 500) {
                title = "Server Error";
                messages = ["Our systems are having trouble. Please contact support."];
            }
        } else if (err.request) {
            messages = ["Unable to reach the server. Please check your internet connection."];
        }

        setErrorState({ show: true, title, messages });
    };

    const [locations, setLocations] = useState([]);
    const [artists, setArtists] = useState([]);
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8082/locations`)
            .then(res => {
                console.log("Fetched locations:", res.data);
                setLocations(res.data);
            })
            .catch(err => {
                console.error("Error fetching venue details:", err);
            });
    }, []);

    useEffect(() => {
        axios.get(`http://localhost:8082/artists`)
            .then(res => {
                setArtists(res.data);
            })
            .catch(err => {
                console.error("Error fetching artists:", err);
            });
    }, []);


    useEffect(() => {
        axios.get(`http://localhost:8082/categories`)
            .then(res => {
                setCategories(res.data);
            })
            .catch(err => {
                console.error("Error fetching categories:", err);
            });
    }, []);


    const handleCategoryChange = (id) => {
        setEventData(prev => ({
            ...prev,
            categories: prev.categories.includes(id)
                ? prev.categories.filter(catId => catId !== id)
                : [...prev.categories, id]
        }));
    };

    const updateLocation = (index, field, value) => {
        const newLocations = [...eventData.eventLocations];
        console.log(`Updating index ${index}, field ${field} with value:`, value);
        newLocations[index][field] = (field === 'price' || field === 'tickets' || field === 'locationId')
            ? Number(value) : value;
        setEventData({ ...eventData, eventLocations: newLocations });
    };

    const addLocation = () => {
        setEventData({
            ...eventData,
            eventLocations: [...eventData.eventLocations, { locationId: '', eventDate: '', tickets: 1, price: 0 }]
        });
    };

    const createEvent = () => {
        const payload = {
            eventName: eventData.eventName,
            description: eventData.description,
            categories: eventData.categories,
            artistId: eventData.artistId, // 
            eventLocations: eventData.eventLocations.map(loc => ({
                locationId: loc.locationId,
                eventDate: loc.date || loc.eventDate, // Ensure this matches your LocalDateTime field
                tickets: loc.tickets,
                price: loc.price
            }))
        };
        console.log("Event Data State:", eventData);
        axios.post(`http://localhost:8082/events`, payload)
            .then(res => {
                // This only runs for 200-299 status codes
                console.log("Event created successfully:", res.data);
                alert("Event created successfully!");
            })
            .catch(err => handleError(err));
    }

    const removeLocation = (index) => {
        if (eventData.eventLocations.length > 1) {
            const newLocs = eventData.eventLocations.filter((_, i) => i !== index);
            setEventData({ ...eventData, eventLocations: newLocs });
        }
    };

    return (
        <div style={styles.page}>
            <div style={styles.container}>
                <header style={styles.header}>
                    <h1 style={styles.title}>List a New Event</h1>
                    <p style={styles.subtitle}>Fill in the details below to publish your event on Evently.</p>
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
                                placeholder="e.g. Molec: Summer Night Plovdiv"
                                onChange={(e) => setEventData({ ...eventData, eventName: e.target.value })}
                            />
                        </div>
                        <div style={styles.inputGroup}>
                            <label style={styles.label}>Description</label>
                            <textarea
                                style={{ ...styles.input, height: '100px', resize: 'none' }}
                                placeholder="What is this event about?"
                                onChange={(e) => setEventData({ ...eventData, description: e.target.value })}
                            />
                        </div>
                    </section>

                    {/* SECTION 2: TALENT & CATEGORY */}
                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>2. Talent & Classification</h3>
                        <div style={styles.grid2}>
                            <div style={styles.inputGroup}>
                                <label style={styles.label}>Artist / Performer</label>
                                <select style={styles.input} onChange={(e) => setEventData({ ...eventData, artistId: Number(e.target.value) })}>
                                    <option value="">Choose Artist</option>
                                    {artists.map(a => <option key={a.id} value={a.id}>{a.name}</option>)}
                                </select>
                            </div>
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
                                    <select style={styles.input} value={loc.locationId} onChange={(e) => updateLocation(index, 'locationId', e.target.value)}>
                                        <option value="">Select Location</option>
                                        {locations.map(l => <option key={l.id} value={l.id}>{l.name}</option>)}
                                    </select>
                                </div>
                                <div style={{ flex: 2 }}>
                                    <label style={styles.miniLabel}>Date & Time</label>
                                    <input type="datetime-local" style={styles.input} onChange={(e) => updateLocation(index, 'eventDate', e.target.value)} />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Tickets</label>
                                    <input type="number" style={styles.input} onChange={(e) => updateLocation(index, 'tickets', e.target.value)} />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Price (USD)</label>
                                    <input type="number" style={styles.input} onChange={(e) => updateLocation(index, 'price', e.target.value)} />
                                </div>
                                {eventData.eventLocations.length > 1 && (
                                    <button type="button" onClick={() => removeLocation(index)} style={styles.removeBtn}>×</button>
                                )}
                            </div>
                        ))}
                        <button type="button" onClick={addLocation} style={styles.addBtn}>+ Add Another Location</button>
                    </section>

                    <button type="button" onClick={() => createEvent()}>Publish Event</button>
                </form>
            </div>

            <>
                <ErrorModal
                    show={errorState.show}
                    title={errorState.title}
                    messages={errorState.messages}
                    onClose={() => setErrorState({ ...errorState, show: false })}
                />
                {/* Your existing form code */}
            </>
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

export default CreateEvent;