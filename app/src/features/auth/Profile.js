import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const Profile = () => {
    const [followedVenues, setFollowedLocations] = useState([]);
    const [recommendedEvents, setRecommendedEvents] = useState([]); 
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
                    {/* Лява колона - Профил */}
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
                    </div>

                    {/* Дясна колона - Динамично съдържание */}
                    <div className="col-lg-8">

                        {/* СЕКЦИЯ: ЛЮБИМИ ЛОКАЦИИ */}
                        <div className="card border-0 shadow-sm p-4 mb-4">
                            <h5 className="fw-bold mb-3"><i className="bi bi-geo-alt-fill text-primary me-2"></i>Favorite Venues</h5>
                            <div className="row g-2">
                                {followedVenues.map(venue => (
                                    <div key={venue.id} className="col-md-6">
                                        <div className="p-2 border rounded-3 d-flex align-items-center">
                                            <i className="bi bi-building me-2 ps-2 text-secondary"></i>
                                            <span className="fw-medium">{venue.name}</span>
                                        </div>
                                    </div>
                                ))}
                                {followedVenues.length === 0 && <p className="text-muted small ps-2">No venues followed yet.</p>}
                            </div>
                        </div>

                        {/* СЕКЦИЯ: ПРЕДЛОЖЕНИЯ ЗА ТЕБ (Events based on likes) */}
                        <div className="card border-0 shadow-sm p-4">
                            <h5 className="fw-bold mb-4 d-flex justify-content-between align-items-center">
                                <span><i className="bi bi-stars text-warning me-2"></i>Events For You</span>
                                <Link to="/events" className="btn btn-sm btn-link text-decoration-none">View All</Link>
                            </h5>

                            <div className="list-group list-group-flush">
                                {recommendedEvents.map(event => (
                                    <div key={event.id} className="list-group-item border-0 px-0 mb-3 transition-hover">
                                        <div className="d-flex align-items-center">
                                            <div className="bg-primary text-white rounded-3 p-2 text-center me-3" style={{ minWidth: '60px' }}>
                                                <span className="d-block fw-bold h5 mb-0">{new Date(event.date).getDate()}</span>
                                                <span className="small text-uppercase">{new Date(event.date).toLocaleString('default', { month: 'short' })}</span>
                                            </div>
                                            <div className="flex-grow-1">
                                                <h6 className="mb-0 fw-bold">{event.name}</h6>
                                                <small className="text-muted">{event.venueName} • {event.categoryName}</small>
                                            </div>
                                            <Link to={`/events/${event.id}`} className="btn btn-sm btn-outline-primary rounded-pill">Tickets</Link>
                                        </div>
                                    </div>
                                ))}
                                {recommendedEvents.length === 0 && <p className="text-center py-4 text-muted">Follow some categories to get recommendations!</p>}
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default Profile;