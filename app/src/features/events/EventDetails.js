import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { ROUTES } from '../../constants/routes';
import axiosClient from '../../api/axiosClient';
import Spinner from '../../components/layout/Spinner';
import EventLocationListItem from '../events/EventLocationListItem';


const EventDetails = () => {
    const [isAdmin] = useState(localStorage.getItem("userRole") === "ADMIN");
    const { id } = useParams();
    const [event, setEvent] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        axiosClient.get(`${ROUTES.EVENTS.DETAILS(id)}`)
            .then(res => {
                console.log("Fetched event details:", res.data);
                setEvent(res.data);
                setIsLoading(false);
            })
            .catch(err => {
                console.error("Error fetching event:", err);
                setIsLoading(false);
            });
    }, [id]);

    if (isLoading) {
        return <Spinner message="Loading event details..." />;
    }

    return (
        <div className="bg-white min-vh-100">
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-4 mb-4 mb-lg-0">
                            <img
                                src={event.eventImageUrl}
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
                                {event.eventDescription}
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
                                    <Link to={ROUTES.EVENTS.ADMIN_EDIT(id)} className="btn btn-outline-primary btn-lg rounded-pill px-5">
                                        Edit Event
                                    </Link>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="dates" className="py-5">
                <div className="container">
                    <div className="mb-5">
                        <h2 className="fw-bold h1">Calendar</h2>
                        <div className="bg-primary rounded" style={{ height: '4px', width: '60px' }}></div>
                    </div>

                    <div className="row g-4">
                        {event.eventLocations && event.eventLocations.length > 0 ? (
                            event.eventLocations.map((loc) => (
                                <EventLocationListItem key={loc.id} loc={loc} />
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