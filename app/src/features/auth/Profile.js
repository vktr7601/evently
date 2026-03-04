import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const Profile = () => {
    const [user, setUser] = useState({});
    useEffect(() => {
        axiosClient.get(`${ROUTES.AUTH.PROFILE}`)
            .then(res => {
                setUser(res.data);
            })
            .catch(err => console.error(err));
    }, []);

    return (
        <div className="bg-light min-vh-100 py-5">
            <div className="container">
                <div className="row">
                    <div className="col-lg-4">
                        <div className="card border-0 shadow-sm text-center p-4 mb-4">
                            <img src={user.profileImg} className="rounded-circle mx-auto mb-3 shadow-sm" style={{ width: '100px' }} alt="profile" />
                            <h4 className="fw-bold">{user.username}</h4>
                            <p className="text-muted small">{user.email}</p>
                            <hr />
                            <div className="text-start">
                                <h6 className="fw-bold small text-uppercase text-primary">My Interests</h6>
                                <div className="d-flex flex-wrap gap-2 mt-2">
                                    {['Rock', 'Techno', 'Jazz'].map(cat => (
                                        <span key={cat} className="badge bg-white text-dark border shadow-sm">#{cat}</span>
                                    ))}
                                </div>
                            </div>
                        </div>

                        <div className="card border-0 shadow-sm p-4 mb-4">
                            <h6 className="fw-bold small text-uppercase text-primary mb-3">Quick Links</h6>
                            <div className="list-group list-group-flush">
                                <Link to={ROUTES.ORDERS.BASE} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-bag-fill text-primary me-3"></i>
                                    <span className="fw-medium">My Orders</span>
                                </Link>
                                <Link to={ROUTES.PAYMENTS.PAYMENT_TRANSACTIONS} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-credit-card-fill text-primary me-3"></i>
                                    <span className="fw-medium">My Transactions</span>
                                </Link>
                                <Link to={ROUTES.TICKETS.USER} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-ticket-perforated-fill text-primary me-3"></i>
                                    <span className="fw-medium">My Tickets</span>
                                </Link>
                                <Link to={ROUTES.EVENTS.FOLLOWED} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-heart-fill text-primary me-3"></i>
                                    <span className="fw-medium">Suggested Events</span>
                                </Link>
                                <Link to={ROUTES.NOTIFICATIONS.BASE} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-inbox-fill text-primary me-3"></i>
                                    <span className="fw-medium">Inbox</span>
                                </Link>
                                <Link to={ROUTES.PROMO_CODES.USER} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-ticket-perforated-fill text-primary me-3"></i>
                                    <span className="fw-medium">My Promo Codes</span>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Profile;