import axios from "axios";
import { useState, useEffect } from "react";



const EventsAdmin = () => {
    const [formData, setFormData] = useState({
        event_name: '',
        description: '',
        performer: '',
        categories: '', // We'll split this string into an array on submit
        locations: [
            { venue: '', date: '', available_tickets: 0, price: 0.0 }
        ]
    });

    const [loading, setLoading] = useState(false);

    // Handle simple inputs
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    // Handle nested location inputs
    const handleLocationChange = (index, e) => {
        const newLocations = [...formData.locations];
        newLocations[index][e.target.name] = e.target.value;
        setFormData({ ...formData, locations: newLocations });
    };

    const addLocation = () => {
        setFormData({
            ...formData,
            locations: [...formData.locations, { venue: '', date: '', available_tickets: 0, price: 0.0 }]
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        // Prepare the payload (converting categories string to array)
        const payload = {
            ...formData,
            categories: formData.categories.split(',').map(c => c.trim()),
            locations: formData.locations.map(loc => ({
                ...loc,
                available_tickets: parseInt(loc.available_tickets),
                price: parseFloat(loc.price)
            }))
        };

        try {
            //await api.post('/events', payload);
            alert("Event created successfully!");
        } catch (err) {
            console.error(err);
            alert("Error creating event.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-5 mb-5" style={{ maxWidth: '800px' }}>
            <div className="card shadow-sm p-4">
                <h2 className="mb-4 fw-bold">Create New Event</h2>
                <form onSubmit={handleSubmit}>
                    
                    <div className="mb-3">
                        <label className="form-label">Event Name</label>
                        <input name="event_name" className="form-control" onChange={handleChange} required placeholder="e.g. Taylor Swift - Eras Tour" />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Description</label>
                        <textarea name="description" className="form-control" rows="3" onChange={handleChange} required />
                    </div>

                    <div className="row">
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Performer</label>
                            <input name="performer" className="form-control" onChange={handleChange} required />
                        </div>
                        <div className="col-md-6 mb-3">
                            <label className="form-label">Categories (comma separated)</label>
                            <input name="categories" className="form-control" placeholder="Music, Dance" onChange={handleChange} required />
                        </div>
                    </div>

                    <hr className="my-4" />
                    <h5 className="mb-3">Locations & Dates</h5>

                    {formData.locations.map((loc, index) => (
                        <div key={index} className="border rounded p-3 mb-3 bg-light">
                            <div className="row g-3">
                                <div className="col-md-6">
                                    <label className="small fw-bold">Venue</label>
                                    <input name="venue" className="form-control form-control-sm" onChange={(e) => handleLocationChange(index, e)} required />
                                </div>
                                <div className="col-md-6">
                                    <label className="small fw-bold">Date & Time</label>
                                    <input type="datetime-local" name="date" className="form-control form-control-sm" onChange={(e) => handleLocationChange(index, e)} required />
                                </div>
                                <div className="col-md-6">
                                    <label className="small fw-bold">Tickets Available</label>
                                    <input type="number" name="available_tickets" className="form-control form-control-sm" onChange={(e) => handleLocationChange(index, e)} required />
                                </div>
                                <div className="col-md-6">
                                    <label className="small fw-bold">Price ($)</label>
                                    <input type="number" step="0.01" name="price" className="form-control form-control-sm" onChange={(e) => handleLocationChange(index, e)} required />
                                </div>
                            </div>
                        </div>
                    ))}

                    <button type="button" className="btn btn-outline-primary btn-sm mb-4" onClick={addLocation}>
                        + Add Another Location
                    </button>

                    <button type="submit" className="btn btn-dark w-100 py-2 fw-bold" disabled={loading}>
                        {loading ? 'Processing...' : 'Publish Event'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default EventsAdmin;