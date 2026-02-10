import axios from 'axios';
import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import './EventItem.css';

const EventList = () => {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get("http://localhost:9000/events")
            .then(res => {
                const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
                setEvents(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return (
            <div className="text-center my-5">
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        );
    }

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <h2 className="fw-bold text-dark mb-0">Upcoming Events</h2>
                <span className="badge bg-primary rounded-pill">{events.length} Events</span>
            </div>

            <div className="row">
                {events.map(event => (
                    <div key={event.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <EventItem event={event}/>
                    </div>
                ))}
            </div>
        </div>
    );
};

const EventItem = ({event}) => {
    const hasImage = event.imageUrl && event.imageUrl.trim() !== "";

    return (
        <div className="card h-100 shadow-sm border-0 transition-hover">
            {hasImage ? (
                <img
                    src={event.imageUrl}
                    className="card-img-top"
                    alt={event.name}
                    style={{height: '180px', objectFit: 'cover'}}
                    onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = "https://placehold.co/600x400?text=Event+Image";
                    }}
                />
            ) : (
                <div
                    className="card-img-top"
                    style={{
                        height: '180px',
                        background: 'linear-gradient(45deg, #0d6efd, #6610f2)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}
                >
                    <i className="bi bi-calendar-event text-white fs-1"></i>
                </div>
            )}

            <div className="card-body d-flex flex-column p-4">
                <div className="d-flex flex-wrap gap-2 mb-3">
                    {event.categories?.map((x) => (
                        <Link
                            key={x.id}
                            to={`/events?category=${encodeURIComponent(x.name)}`}
                            className="badge bg-light text-primary border border-primary-subtle text-decoration-none"
                            style={{fontSize: '0.75rem'}}
                        >
                            {x.name || 'General'}
                        </Link>
                    ))}
                </div>

                <h5 className="card-title fw-bold mb-1">{event.name}</h5>

                <p className="text-muted small mb-3">
                    <i className="bi bi-person-fill me-2 text-primary"></i>
                    {event.performer || 'TBA'}
                </p>

                {/* Optional: Add a short description if your event has one */}
                {event.description && (
                    <p className="text-muted small mb-3 line-clamp-2" style={{
                        display: '-webkit-box', WebkitLineClamp: '2', WebkitBoxOrient: 'vertical', overflow: 'hidden'
                    }}>
                        {event.description}
                    </p>
                )}

                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-between">
                    <span className="text-dark fw-bold small">
                        <i className="bi bi-clock me-1"></i> Details
                    </span>
                    <Link
                        to={`/events/${event.id}`}
                        className="btn btn-sm btn-primary rounded-pill px-4"
                    >
                        Explore
                    </Link>
                </div>
            </div>
        </div>
    );
};
export default EventList;