import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

const LocationDetails = () => {
    const { id } = useParams();
    const [venue, setVenue] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:9000/locations/${id}`)
            .then(res => {
                setVenue(res.data);
            })
            .catch(err => {
                console.error("Error fetching location:", err);
            });
    }, [id]);

    if (!venue) return <div className="text-center py-5 mt-5"><div className="spinner-border text-primary"></div></div>;

    return (
        <div className="bg-white min-vh-100">
            {/* Venue Hero Section */}
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-5 mb-4 mb-lg-0">
                            <img
                                src={venue.imageUrl}
                                alt={venue.name}
                                className="img-fluid rounded-4 shadow-lg w-100"
                                style={{ objectFit: 'cover', height: '400px' }}
                            />
                        </div>
                        <div className="col-lg-7 ps-lg-5">
                            <h6 className="text-primary fw-bold text-uppercase tracking-wider">
                                <i className="bi bi-geo-alt-fill me-2"></i>Premier Venue
                            </h6>
                            <h1 className="display-4 fw-black text-dark mb-3">{venue.name}</h1>
                            <p className="fs-5 text-secondary mb-4" style={{ lineHeight: '1.8' }}>
                                {venue.description}
                            </p>
                            <div className="d-flex gap-3">
                                <a href="#schedule" className="btn btn-primary btn-lg rounded-pill px-5">View Full Schedule</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Events at this Location Section */}
            <section id="schedule" className="py-5">
                <div className="container">
                    <div className="mb-5 text-center">
                        <h2 className="fw-bold h1">Events at this Venue</h2>
                        <div className="bg-primary rounded mx-auto" style={{ height: '4px', width: '60px' }}></div>
                    </div>

                    <div className="row g-4">
                        {/* Note the key change to venue.eventlocationsdto to match your JSON */}
                        {venue.eventlocationsdto && venue.eventlocationsdto.length > 0 ? (
                            venue.eventlocationsdto.map((loc) => (
                                <div key={loc.eventLocationId} className="col-12">
                                    <div className={`card border-0 shadow-sm p-3 transition-hover ${loc.status === 'SOLD_OUT' ? 'opacity-75' : ''}`}>
                                        <div className="row align-items-center text-center text-md-start">
                                            
                                            {/* Date Info */}
                                            <div className="col-md-2 border-end-md text-center">
                                                <h4 className="fw-bold mb-0 text-dark">
                                                    {new Date(loc.eventStartTime).toLocaleDateString('en-US', { day: '2-digit', month: 'short' })}
                                                </h4>
                                                <small className="text-muted text-uppercase fw-bold">
                                                    {new Date(loc.eventStartTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                                </small>
                                            </div>

                                            {/* Event Info */}
                                            <div className="col-md-5">
                                                <span className="badge bg-primary-subtle text-primary mb-1">Live Performance</span>
                                                <h5 className="fw-bold mb-0 text-dark">{loc.eventName}</h5>
                                                <p className="text-muted small mb-0">Experience world-class music in a historical setting.</p>
                                            </div>

                                            {/* Pricing */}
                                            <div className="col-md-2">
                                                <div className="text-muted small">Starting at</div>
                                                <span className="fs-4 fw-black text-dark">€{loc.pricePerTicket.toFixed(2)}</span>
                                            </div>

                                            {/* Action Button */}
                                            <div className="col-md-3 text-md-end mt-3 mt-md-0">
                                                {loc.status === 'AVAILABLE' ? (
                                                    <Link to={`/events/${loc.eventId}`} className="btn btn-primary rounded-pill px-5 py-2 shadow-sm fw-bold">
                                                        Get Tickets
                                                    </Link>
                                                ) : (
                                                    <button className="btn btn-secondary rounded-pill px-5 py-2 disabled" disabled>
                                                        Sold Out
                                                    </button>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="col-12 text-center py-5">
                                <i className="bi bi-calendar-x fs-1 text-muted mb-3 d-block"></i>
                                <p className="text-muted fs-5">There are currently no scheduled events for this venue.</p>
                                <Link to="/events" className="btn btn-outline-primary rounded-pill mt-2">Browse Other Events</Link>
                            </div>
                        )}
                    </div>
                </div>
            </section>
        </div>
    );
};

export default LocationDetails;