import NotificationList from './NotificationList';
import Hero from '../../components/layout/Hero';
import { useState } from 'react';
import axios from 'axios';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const Notifications = () => {
    const [selectedNote, setSelectedNote] = useState(null);
    const handleDelete = (id) => {
        axiosClient.delete(`${ROUTES.NOTIFICATIONS.SPECIFIC(id)}`)
        .then(res => {
            if (selectedNote && selectedNote.id === id) {
                setSelectedNote(null);
            }
        })
        .catch(err => console.error("Delete error:", err));
    };
    const handleMarkRead = (id) => {
        axiosClient.put(`${ROUTES.NOTIFICATIONS.SPECIFIC(id)}`)
        .then(res => {
            if (selectedNote && selectedNote.id === id) {
                setSelectedNote(null);
            }
        })
        .catch(err => console.error("Mark read error:", err));
    };

    return (
        <div className="bg-light min-vh-100">
            <Hero
                badge="🔔 Alert Center"
                title="Your"
                highlight="Notifications"
                subtitle="Stay up to date with your ticket purchases and favorite artists."
                primaryAction={{ text: "Back to Home", link: "/" }}
                secondaryAction={{ text: "Settings", link: "/settings" }}
            />

            <div className="container-fluid px-lg-5" style={{ marginTop: "-60px", position: "relative", zIndex: "10" }}>
                <div className="card shadow-lg border-0 rounded-4 overflow-hidden">
                    <div className="row g-0" style={{ minHeight: '600px' }}>
                        
                        <div className="col-lg-4 border-end bg-white overflow-auto" style={{ maxHeight: '800px' }}>
                            <div className="card-header bg-white py-3 px-4 border-bottom sticky-top">
                                <h5 className="fw-black mb-0">Recent Updates</h5>
                            </div>
                            <NotificationList 
                                onSelectNote={setSelectedNote} 
                                activeId={selectedNote?.id} 
                            />
                        </div>

                        <div className="col-lg-8 bg-white d-flex flex-column">
                            {selectedNote ? (
                                <div className="p-4 p-md-5 animate__animated animate__fadeIn">
                                    <div className="d-flex justify-content-between align-items-start mb-4">
                                        <div>
                                            <h2 className="display-6 fw-bold text-dark">{selectedNote.title}</h2>
                                            <p className="text-muted mb-0">
                                                <i className="bi bi-clock me-2"></i>
                                                {new Date(selectedNote.createdAt).toLocaleString()}
                                            </p>
                                        </div>
                                    </div>

                                    <hr className="my-4 opacity-75" />

                                    <div className="fs-5 text-secondary mb-5" style={{ lineHeight: '1.8' }}>
                                        <div dangerouslySetInnerHTML={{ __html: selectedNote.htmlBody }} />
                                    </div>

                                    <div className="mt-auto pt-4 d-flex gap-3">
                                        <button onClick={() => handleDelete(selectedNote.id)} className="btn btn-outline-light text-danger border-0 px-4">
                                            <i className="bi bi-trash3 me-2"></i> Delete
                                        </button>
                                        {selectedNote.read !== 'true' && (
                                        <button onClick={() => handleMarkRead(selectedNote.id)} className="btn btn-outline-light text-success border-0 px-4">
                                            <i className="bi bi-check2-circle me-2"></i> Mark as Read
                                        </button>
                                        )}
                                    </div>
                                </div>
                            ) : (
                                <div className="h-100 d-flex flex-column align-items-center justify-content-center text-muted p-5 bg-light-subtle">
                                    <div className="text-center opacity-50">
                                        <i className="bi bi-envelope-open display-1"></i>
                                        <h4 className="mt-3 fw-bold">No Notification Selected</h4>
                                        <p>Click on a message from the list to view its contents.</p>
                                    </div>
                                </div>
                            )}
                        </div>

                    </div>
                </div>
            </div>
            <div className="py-5"></div>
        </div>
    );
};

export default Notifications;;
