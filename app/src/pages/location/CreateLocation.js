import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ErrorModal from '../../components/system/ErrorModal';

const CreateLocation = () => {
    // 1. Unified State: One Location, Multiple Event Assignments
    const [locationData, setLocationData] = useState({
        name: '',
        description: ''
    });

    const [eventAssignments, setEventAssignments] = useState([
        { eventId: '', eventDate: '', tickets: 0, price: 0 }
    ]);

    const [availableEvents, setAvailableEvents] = useState([]);
    const [errorState, setErrorState] = useState({ show: false, title: '', messages: [] });

    // Fetch existing events for the dropdowns
    useEffect(() => {
        axios.get(`http://localhost:8082/events`)
            .then(res => setAvailableEvents(res.data))
            .catch(err => console.error("Fetch error:", err));
    }, []);

    // 2. Dynamic Row Logic (Same pattern as your original code)
    const updateAssignment = (index, field, value) => {
        const updated = [...eventAssignments];
        updated[index][field] = (field === 'price' || field === 'tickets') ? Number(value) : value;
        setEventAssignments(updated);
    };

    const addEventRow = () => {
        setEventAssignments([...eventAssignments, { eventId: '', eventDate: '', tickets: 0, price: 0 }]);
    };

    const removeEventRow = (index) => {
        setEventAssignments(eventAssignments.filter((_, i) => i !== index));
    };

    // 3. Sequential Submission Logic
    const handleSubmit = async () => {
        try {
            // STEP 1: Create Location
            const locRes = await axios.post(`http://localhost:8082/locations`, locationData);
            const newLocationId = locRes.data.id;

            // STEP 2: Create all event assignments
            // We use Promise.all to send all assignments to the server simultaneously
            const assignmentPromises = eventAssignments.map(assign => {
                const payload = {
                    ...assign,
                    locationId: newLocationId
                };
                // Assuming your endpoint is POST /events/{id}/locations
                return axios.post(`http://localhost:8082/events/${assign.eventId}/locations`, payload);
            });

            await Promise.all(assignmentPromises);
            
            alert("Location created and all events scheduled!");
            // Reset logic here...
        } catch (err) {
            handleError(err);
        }
    };

    const handleError = (err) => {
        const messages = err.response?.data ? Object.values(err.response.data) : ["Submission failed"];
        setErrorState({ show: true, title: "Error", messages });
    };

    return (
        <div style={styles.page}>
            <div style={styles.container}>
                <header style={styles.header}>
                    <h1 style={styles.title}>New Venue & Schedule</h1>
                </header>

                <form style={styles.formCard}>
                    {/* SECTION: LOCATION INFO */}
                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>1. Location Details</h3>
                        <input
                            type="text"
                            placeholder="Location Name"
                            style={styles.input}
                            onChange={(e) => setLocationData({...locationData, name: e.target.value})}
                        />
                    </section>

                    {/* SECTION: MULTIPLE EVENT ASSIGNMENTS */}
                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>2. Scheduled Events at this Venue</h3>
                        {eventAssignments.map((item, index) => (
                            <div key={index} style={styles.locationRow}>
                                <div style={{ flex: 2 }}>
                                    <label style={styles.miniLabel}>Select Event</label>
                                    <select 
                                        style={styles.input} 
                                        value={item.eventId}
                                        onChange={(e) => updateAssignment(index, 'eventId', e.target.value)}
                                    >
                                        <option value="">Choose Event</option>
                                        {availableEvents.map(ev => <option key={ev.id} value={ev.id}>{ev.eventName}</option>)}
                                    </select>
                                </div>
                                <div style={{ flex: 2 }}>
                                    <label style={styles.miniLabel}>Date</label>
                                    <input 
                                        type="datetime-local" 
                                        style={styles.input} 
                                        onChange={(e) => updateAssignment(index, 'eventDate', e.target.value)}
                                    />
                                </div>
                                <div style={{ flex: 1 }}>
                                    <label style={styles.miniLabel}>Price</label>
                                    <input 
                                        type="number" 
                                        style={styles.input} 
                                        onChange={(e) => updateAssignment(index, 'price', e.target.value)}
                                    />
                                </div>
                                {eventAssignments.length > 1 && (
                                    <button type="button" onClick={() => removeEventRow(index)} style={styles.removeBtn}>×</button>
                                )}
                            </div>
                        ))}
                        <button type="button" onClick={addEventRow} style={styles.addBtn}>+ Add Another Event Date</button>
                    </section>

                    <button type="button" style={styles.submitBtn} onClick={handleSubmit}>
                        Save Venue and All Events
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
    // ... same styles as your CreateEvent component ...
    page: { backgroundColor: '#f4f7fa', minHeight: '100vh', padding: '60px 20px', fontFamily: '"Inter", sans-serif' },
    container: { maxWidth: '750px', margin: '0 auto' },
    header: { marginBottom: '40px', textAlign: 'center' },
    title: { fontSize: '32px', fontWeight: '800', color: '#1e293b' },
    subtitle: { color: '#64748b' },
    formCard: { backgroundColor: '#fff', padding: '40px', borderRadius: '24px', boxShadow: '0 20px 25px -5px rgba(0,0,0,0.05)' },
    section: { marginBottom: '30px', borderBottom: '1px solid #f1f5f9', paddingBottom: '20px' },
    sectionTitle: { fontSize: '16px', fontWeight: '700', color: '#2563eb', marginBottom: '15px' },
    inputGroup: { marginBottom: '20px' },
    label: { display: 'block', fontWeight: '600', marginBottom: '8px', fontSize: '14px' },
    input: { width: '100%', padding: '12px', borderRadius: '10px', border: '1px solid #e2e8f0' },
    grid2: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' },
    submitBtn: { width: '100%', backgroundColor: '#2563eb', color: '#fff', padding: '16px', borderRadius: '12px', fontWeight: '700', border: 'none', cursor: 'pointer' }
};

export default CreateLocation;