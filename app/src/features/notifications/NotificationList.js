import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const NotificationsPage = () => {
    const [notifications, setNotifications] = useState([]);
    const [selectedNote, setSelectedNote] = useState(null); // Track which notification is open
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get("http://localhost:8083/notifications", {
            headers: { 'X-User-Id': '6' }
        })
        .then(res => {
            setNotifications(res.data);
            if (res.data.length > 0) setSelectedNote(res.data[0]); // Auto-select first
            setLoading(false);
        })
        .catch(err => {
            console.error("Error:", err);
            setLoading(false);
        });
    }, []);

    return (
        <div className="container-fluid p-0 vh-100 bg-white">
            <div className="row g-0 h-100">
                
                {/* LEFT SIDE: The List (Inbox) */}
                <div className="col-md-4 border-end h-100 overflow-auto" style={{ backgroundColor: '#f8f9fa' }}>
                    <div className="p-3 border-bottom bg-white sticky-top">
                        <h5 className="fw-bold mb-0">Notifications ({notifications.length})</h5>
                    </div>

                    {loading ? (
                        <div className="text-center p-5"><div className="spinner-border spinner-border-sm text-primary"></div></div>
                    ) : (
                        <div className="list-group list-group-flush">
                            {notifications.map((note) => (
                                <button
                                    key={note.id}
                                    onClick={() => setSelectedNote(note)}
                                    className={`list-group-item list-group-item-action border-bottom p-3 ${selectedNote?.id === note.id ? 'bg-white shadow-sm' : 'bg-transparent'}`}
                                >
                                    <div className="d-flex justify-content-between">
                                        <small className={`fw-bold ${selectedNote?.id === note.id ? 'text-primary' : 'text-dark'}`}>
                                            {note.eventName}
                                        </small>
                                        <small className="text-muted text-nowrap ms-2">
                                            {new Date(note.createdAt).toLocaleDateString()}
                                        </small>
                                    </div>
                                    <p className="mb-0 small text-truncate text-secondary">
                                        {note.message || `New event at ${note.locationName}`}
                                    </p>
                                </button>
                            ))}
                        </div>
                    )}
                </div>

                {/* RIGHT SIDE: The Content (Reading Pane) */}
                <div className="col-md-8 h-100 bg-white overflow-auto">
                    {selectedNote ? (
                        <div className="p-5">
                            <div className="d-flex justify-content-between align-items-center mb-4 pb-3 border-bottom">
                                <div>
                                    <h2 className="fw-bold">{selectedNote.eventName}</h2>
                                    <p className="text-muted mb-0">From: Event Notification Service</p>
                                </div>
                                <span className="text-muted small">
                                    {new Date(selectedNote.createdAt).toLocaleString()}
                                </span>
                            </div>

                            <div className="fs-5 text-dark mb-5" style={{ lineHeight: '1.6' }}>
                                <p>{selectedNote.message || `An artist you follow just announced a new performance at ${selectedNote.locationName}!`}</p>
                            </div>

                            <div className="d-flex gap-3">
                                <Link to={`/events/${selectedNote.eventId}`} className="btn btn-primary px-4">
                                    View Full Event Details
                                </Link>
                                <button className="btn btn-outline-danger">
                                    Delete Notification
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div className="h-100 d-flex align-items-center justify-content-center text-muted">
                            <div className="text-center">
                                <i className="bi bi-envelope-open display-1 text-light"></i>
                                <p>Select a notification to read it</p>
                            </div>
                        </div>
                    )}
                </div>

            </div>
        </div>
    );
};

export default NotificationsPage;