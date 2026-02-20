import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const NotificationsPage = () => {
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Заявка към твоя нов Notification Service (порт 8083)
        axios.get("http://localhost:8083/notifications", {
            headers: { 'X-User-Id': '1' } // Тук подаваш реалното ID
        })
        .then(res => {
            setNotifications(res.data);
            setLoading(false);
        })
        .catch(err => {
            console.error("Error fetching notifications:", err);
            setLoading(false);
        });
    }, []);

    const markAsRead = (id) => {
        // Опционално: Логика за изтриване или отбелязване като прочетено
        setNotifications(notifications.filter(n => n.id !== id));
    };

    return (
        <div className="bg-light min-vh-100 py-5">
            <div className="container">
                <div className="row justify-content-center">
                    <div className="col-lg-8">
                        
                        {/* Header на секцията */}
                        <div className="d-flex align-items-center justify-content-between mb-4">
                            <h2 className="fw-black text-dark mb-0">
                                <i className="bi bi-bell-fill text-primary me-2"></i>
                                Notifications
                            </h2>
                            <span className="badge bg-primary rounded-pill">
                                {notifications.length} New
                            </span>
                        </div>

                        {loading ? (
                            <div className="text-center py-5">
                                <div className="spinner-border text-primary"></div>
                            </div>
                        ) : notifications.length > 0 ? (
                            <div className="d-flex flex-column gap-3">
                                {notifications.map((note) => (
                                    <div key={note.id} className="card border-0 shadow-sm rounded-4 transition-hover overflow-hidden">
                                        <div className="card-body p-4 d-flex align-items-start">
                                            
                                            {/* Икона според типа на събитието */}
                                            <div className="bg-primary-subtle p-3 rounded-circle me-3">
                                                <i className="bi bi-calendar-event text-primary fs-4"></i>
                                            </div>

                                            <div className="flex-grow-1">
                                                <div className="d-flex justify-content-between align-items-start">
                                                    <h6 className="fw-bold mb-1 text-dark">
                                                        New Event: {note.eventName}
                                                    </h6>
                                                    <small className="text-muted">
                                                        {new Date(note.createdAt).toLocaleDateString()}
                                                    </small>
                                                </div>
                                                <p className="text-secondary small mb-3">
                                                    {note.message || `An artist you follow just announced a new performance at ${note.locationName}!`}
                                                </p>
                                                
                                                <div className="d-flex gap-2">
                                                    <Link to={`/events/${note.eventId}`} className="btn btn-sm btn-primary rounded-pill px-4">
                                                        Check It Out
                                                    </Link>
                                                    <button 
                                                        onClick={() => markAsRead(note.id)}
                                                        className="btn btn-sm btn-outline-secondary rounded-pill border-0"
                                                    >
                                                        Dismiss
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            /* Празно състояние */
                            <div className="text-center py-5 bg-white rounded-4 shadow-sm">
                                <i className="bi bi-mailbox2 display-1 text-light mb-3"></i>
                                <h4 className="text-muted">All caught up!</h4>
                                <p className="text-secondary">No new notifications at the moment.</p>
                                <Link to="/events" className="btn btn-primary rounded-pill mt-3">Explore Events</Link>
                            </div>
                        )}

                    </div>
                </div>
            </div>
        </div>
    );
};

export default NotificationsPage;