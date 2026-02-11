import React, { useEffect, useState } from 'react';
import axios from 'axios';

const NotificationPage = () => {
    const [notifications, setNotifications] = useState([]);
    const [selectedNotif, setSelectedNotif] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchNotifications = async () => {
            const token = localStorage.getItem('userToken');
            console.log(token);

            // 1. Guard clause: Don't even try if there's no token
            if (!token) {
                console.warn("No token found, redirecting or skipping fetch.");
                setLoading(false);
                return;
            }

            try {
                const res = await axios.get('http://localhost:8080/notifications', {
                    headers: {
                        // Correct Header Name: 'Authorization'
                        // Correct Format: 'Bearer <token>'
                        'Authorization': `Bearer ${token}`
                    }
                });

                setNotifications(res.data);

                // Auto-select the first notification if it exists
                if (res.data && res.data.length > 0) {
                    setSelectedNotif(res.data[0]);
                }
            } catch (err) {
                console.error("Failed to fetch notifications:", err);
                // Handle 401 Unauthorized here if the token is expired
                if (err.response?.status === 401) {
                    // localStorage.removeItem('userToken');
                    // navigate('/login');
                }
            } finally {
                setLoading(false);
            }
        };

        fetchNotifications();
    }, []);

    const onClick = (notif) => {
        axios.put(`http://api-gateway/notifications/${notif.id}`)
            .then(() => {
                // 2. АКО заявката е успешна, ъпдейтваме САМО локалния стейт
                setNotifications(prevNotifications =>
                    prevNotifications.map(n =>
                        n.id === notif.id ? { ...n, isRead: true } : n
                    )
                );
            })
            .catch(err => console.error("Error updating notification:", err));
    };



    if (loading) return <div className="p-5 text-center">Loading inbox...</div>;

    return (
        <div className="container-fluid vh-100 p-0 overflow-hidden">
            <div className="row g-0 h-100">

                {/* LEFT SIDE: Message List (Scrollable) */}
                <div className="col-md-4 col-lg-3 border-end bg-white h-100 overflow-auto">
                    <div className="p-3 border-bottom bg-light sticky-top">
                        <h4 className="m-0 fw-bold text-primary">Inbox</h4>
                    </div>

                    <div className="list-group list-group-flush">
                        {notifications.map((notif) => (
                            <button
                                key={notif.id}
                                onClick={() => {
                                    if (notif.isRead) return;
                                    onClick(notif);
                                }}
                                className={`list-group-item list-group-item-action py-3 border-bottom ${selectedNotif?.id === notif.id ? 'bg-primary-subtle border-start border-primary border-4' : ''
                                    }`}
                                style={{ transition: '0.2s' }}
                            >
                                <div className="d-flex justify-content-between small mb-1">
                                    <span className={`fw-bold ${!notif.isRead ? 'text-dark' : 'text-muted'}`}>
                                        {notif.title}
                                    </span>
                                    <span className="text-muted" style={{ fontSize: '0.75rem' }}>
                                        {new Date(notif.createdAt).toLocaleDateString()}
                                    </span>
                                </div>
                                <div
                                    className="text-muted text-truncate small"
                                    dangerouslySetInnerHTML={{ __html: notif.message.substring(0, 50) + '...' }}
                                />
                            </button>
                        ))}
                    </div>
                </div>

                <div className="col-md-8 col-lg-9 bg-light h-100 overflow-auto">
                    {selectedNotif ? (
                        <div className="p-5 mx-auto bg-white shadow-sm my-4 rounded-3" style={{ maxWidth: '800px', minHeight: '80vh' }}>
                            <div className="border-bottom pb-4 mb-4">
                                <h1 className="fw-bold h2">{selectedNotif.title}</h1>
                                <div className="d-flex justify-content-between align-items-center mt-3">
                                    <div className="d-flex align-items-center text-muted">
                                        <div className="bg-secondary rounded-circle text-white d-flex align-items-center justify-content-center me-2" style={{ width: '35px', height: '35px' }}>
                                            {selectedNotif.title.charAt(0)}
                                        </div>
                                        <span>System Notification</span>
                                    </div>
                                    <small className="text-muted">
                                        {new Date(selectedNotif.createdAt).toLocaleString()}
                                    </small>
                                </div>
                            </div>

                            <div
                                className="mail-body pt-2 text-dark lead"
                                style={{ lineHeight: '1.6' }}
                                dangerouslySetInnerHTML={{ __html: selectedNotif.message }}
                            />
                        </div>


                    ) : (
                        <div className="h-100 d-flex flex-column align-items-center justify-content-center text-muted">
                            <i className="bi bi-envelope-open fs-1 mb-3"></i>
                            <p>Select a message to read its content</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default NotificationPage;