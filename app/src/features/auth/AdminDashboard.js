import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';
import Spinner from '../../components/layout/Spinner';
import { NavLink } from 'react-router-dom';
const AdminDashboard = () => {
    const [user, setUser] = useState({});

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
                                <Link to={ROUTES.EVENTS.ADMIN_CREATE} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-bag-fill text-primary me-3"></i>
                                    <span className="fw-medium">Create Event</span>
                                </Link>
                                <Link to={ROUTES.LOCATIONS.ADMIN_CREATE} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-credit-card-fill text-primary me-3"></i>
                                    <span className="fw-medium">Create Location</span>
                                </Link>
                                <Link to={ROUTES.ARTISTS.ADMIN_CREATE} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-ticket-perforated-fill text-primary me-3"></i>
                                    <span className="fw-medium">Create Artist</span>
                                </Link>
                                <Link to={ROUTES.PROMO_CODES.ADMIN} className="list-group-item list-group-item-action border-0 d-flex align-items-center px-0 py-2">
                                    <i className="bi bi-ticket-perforated-fill text-primary me-3"></i>
                                    <span className="fw-medium">Create Promo Code</span>
                                </Link>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-8">
                        <h5 className="fw-bold mb-3">Statistics</h5>
                        <div className="row g-3 mb-4">
                            <div className="col-sm-6 col-md-4">
                                <div className="card border-0 shadow-sm p-3 text-center">
                                    <i className="bi bi-calendar-event fs-3 text-primary"></i>
                                    <h3 className="fw-bold mt-2 mb-0">128</h3>
                                    <p className="text-muted small mb-0">Total Events</p>
                                </div>
                            </div>
                            <div className="col-sm-6 col-md-4">
                                <div className="card border-0 shadow-sm p-3 text-center">
                                    <i className="bi bi-bag-check fs-3 text-success"></i>
                                    <h3 className="fw-bold mt-2 mb-0">1,024</h3>
                                    <p className="text-muted small mb-0">Total Orders</p>
                                </div>
                            </div>
                            <div className="col-sm-6 col-md-4">
                                <div className="card border-0 shadow-sm p-3 text-center">
                                    <i className="bi bi-people fs-3 text-info"></i>
                                    <h3 className="fw-bold mt-2 mb-0">3,456</h3>
                                    <p className="text-muted small mb-0">Total Users</p>
                                </div>
                            </div>
                            <div className="col-sm-6 col-md-4">
                                <div className="card border-0 shadow-sm p-3 text-center">
                                    <i className="bi bi-ticket-perforated fs-3 text-warning"></i>
                                    <h3 className="fw-bold mt-2 mb-0">8,192</h3>
                                    <p className="text-muted small mb-0">Tickets Sold</p>
                                </div>
                            </div>
                            <div className="col-sm-6 col-md-4">
                                <div className="card border-0 shadow-sm p-3 text-center">
                                    <i className="bi bi-currency-dollar fs-3 text-danger"></i>
                                    <h3 className="fw-bold mt-2 mb-0">$54,320</h3>
                                    <p className="text-muted small mb-0">Revenue</p>
                                </div>
                            </div>
                            <div className="col-sm-6 col-md-4">
                                <div className="card border-0 shadow-sm p-3 text-center">
                                    <i className="bi bi-tag fs-3 text-secondary"></i>
                                    <h3 className="fw-bold mt-2 mb-0">42</h3>
                                    <p className="text-muted small mb-0">Active Promo Codes</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminDashboard;