import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';


const EventDetails = () => {
    const [isAdmin, setIsAdmin] = useState(true);
    const { id } = useParams();
    const [event, setEvent] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8082/events/${id}`)
            .then(res => {
                console.log("Fetched event details:", res.data);
                setEvent(res.data);
            })
            .catch(err => {
                console.error("Error fetching event:", err);
            });
    }, [id]);

    const renderBookingButton = (loc) => {
        switch (loc.eventsLocationsStatus) {
            case 'AVAILABLE':
                return (
                    <Link to={`/event-details/${loc.eventLocationId}`} className="btn btn-primary rounded-pill px-5 py-2 shadow-sm">
                        Book Tickets
                    </Link>
                );
            case 'CANCELLED':
                return (
                    <button className="btn btn-outline-danger rounded-pill px-5 py-2 disabled" disabled>
                        Cancelled
                    </button>
                );
            case 'PENDING_TICKETS':
                return (
                    <button className="btn btn-warning rounded-pill px-5 py-2 disabled" disabled>
                        Coming Soon
                    </button>
                );
            default: 
                return (
                    <button className="btn btn-secondary rounded-pill px-5 py-2 disabled" disabled>
                        Sold Out
                    </button>
                );
        }
    };

    if (!event) return <div className="text-center py-5 mt-5"><div className="spinner-border text-primary"></div></div>;

    return (
        <div className="bg-white min-vh-100">
            {/* Hero Section: Event Info */}
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-4 mb-4 mb-lg-0">
                            <img
                                src={event.imageUrl}
                                alt={event.name}
                                className="img-fluid rounded-4 shadow-lg w-100"
                                style={{ objectFit: 'cover', height: '400px' }}
                            />
                        </div>
                        <div className="col-lg-8 ps-lg-5">
                            <div className="d-flex flex-wrap gap-2 mb-3">
                                {event.categories?.map(cat => (
                                    <span key={cat.id} className="badge rounded-pill bg-primary-subtle text-primary px-3 py-2 text-uppercase fw-bold small">
                                        {cat.name}
                                    </span>
                                ))}
                            </div>
                            <h1 className="display-3 fw-black text-dark mb-3">{event.name}</h1>
                            <p className="fs-5 text-secondary mb-4" style={{ lineHeight: '1.8' }}>
                                {event.description}
                            </p>

                            {/* Artist Mention */}
                            <div className="d-flex align-items-center mb-4 p-3 bg-white rounded-3 shadow-sm border" style={{ maxWidth: '400px' }}>
                                <img src={event.artist.imageUrl} alt={event.artist.name} className="rounded-circle me-3" style={{ width: '50px', height: '50px', objectFit: 'cover' }} />
                                <div>
                                    <h6 className="mb-0 text-muted small">Performing Artist</h6>
                                    <Link to={`/artist/${event.artist.id}`} className="fw-bold text-decoration-none text-dark">
                                        {event.artist.name} <i className="bi bi-arrow-right-short"></i>
                                    </Link>
                                </div>
                            </div>

                            <div className="d-flex gap-3 mt-4">
                                <a href="#dates" className="btn btn-primary btn-lg rounded-pill px-5">
                                    View Calendar
                                </a>
                                {isAdmin && (
                                    // <a href="#edit" className="btn btn-outline-primary btn-lg rounded-pill px-5">
                                    //     Edit Event
                                    // </a>
                                    <Link to={`/admin/event-management/${event.id}`} className="btn btn-outline-primary btn-lg rounded-pill px-5">
                                        Edit Event
                                    </Link>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Dates & Locations Section */}
            <section id="dates" className="py-5">
                <div className="container">
                    <div className="mb-5">
                        <h2 className="fw-bold h1">Calendar</h2>
                        <div className="bg-primary rounded" style={{ height: '4px', width: '60px' }}></div>
                    </div>

                    <div className="row g-4">
                        {event.eventLocations && event.eventLocations.length > 0 ? (
                            event.eventLocations.map((loc) => (
                                <div key={loc.eventLocationId} className="col-12">
                                    <div className={`card border-0 shadow-sm p-3 transition-hover ${loc.status === 'SOLD_OUT' ? 'opacity-75' : ''}`}>
                                        <div className="row align-items-center text-center text-md-start">
                                            {/* Date/Time Column */}
                                            <div className="col-md-2 border-end-md">
                                                <h4 className="fw-bold mb-0">
                                                    {new Date(loc.eventStartTime).toLocaleDateString('en-US', { day: '2-digit', month: 'short' })}
                                                </h4>
                                                <small className="text-muted">
                                                    {new Date(loc.eventStartTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                                </small>
                                            </div>

                                            {/* Venue Column */}
                                            <div className="col-md-5">
                                                <h5 className="fw-bold mb-1">{loc.locationName}</h5>
                                                <p className="text-primary mb-0 small text-uppercase fw-bold">
                                                    <i className="bi bi-geo-alt-fill me-1"></i>
                                                    {loc.eventName}
                                                </p>
                                            </div>

                                            {/* Price Column */}
                                            <div className="col-md-2">
                                                <span className="fs-5 fw-bold text-dark">€{loc.pricePerTicket.toFixed(2)}</span>
                                            </div>

                                            {/* Action Column */}
                                            <div className="col-md-3 text-md-end mt-3 mt-md-0">
                                                {renderBookingButton(loc)}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="col-12 text-center py-5">
                                <p className="text-muted">No scheduled showtimes available.</p>
                            </div>
                        )}
                    </div>
                </div>
            </section>
        </div>
    );
};

export default EventDetails;