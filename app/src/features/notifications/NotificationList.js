import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import NotificationListItem from './NotificationListItem';

const NotificationList = ({ onSelectNote, activeId }) => {
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMails = async () => {
            try {
                const token = localStorage.getItem("jwtToken");
                const res = await axios.get('http://localhost:9000/notifications', {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                setNotifications(res.data);
            } catch (err) {
                console.error("Fetch error:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchMails();
    }, []);

    if (loading) {
        return <div className="text-center p-5"><div className="spinner-border text-primary"></div></div>;
    }

    return (
        <div className="list-group list-group-flush">
            <div className="p-3 bg-white border-bottom fw-bold text-uppercase small text-secondary">
                Inbox ({notifications.length})
            </div>
            {notifications.map(note => (
                <div key={note.id} onClick={() => onSelectNote(note)}>
                    <NotificationListItem 
                        data={note} 
                        isActive={activeId === note.id} 
                    />
                </div>
            ))}
        </div>
    );
};

export default NotificationList;