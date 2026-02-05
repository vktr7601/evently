import {useParams, Link} from "react-router-dom";
import {useState, useEffect} from 'react';
import axios from "axios";

const VenueDetails = () => {
    const {venueId} = useParams();
    const [venue, setVenue] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get(`http://localhost:9000/venues/${venueId}`)
            .then(res => {
                setVenue(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching venue details:", err);
                setLoading(false);
            });
    }, [venueId]);

    if (loading) return (
        <div className="text-center my-5 py-5">
            <div className="spinner-border text-primary" role="status"></div>
            <p className="mt-2 text-muted">Loading venue info...</p>
        </div>
    );

    if (!venue) return (
        <div className="container mt-5 text-center">
            <h3>Venue not found</h3>
            <Link to="/venues" className="btn btn-primary mt-3">Back to Venues</Link>
        </div>
    );

    return (
        <div className="container mt-5">
            <div className="row align-items-center mb-5">

                <div className="col-md-8 mt-3 mt-md-0">
                    <nav aria-label="breadcrumb">
                        <ol className="breadcrumb mb-2">
                            <li className="breadcrumb-item"><Link to="/venues">Venues</Link></li>
                            <li className="breadcrumb-item active">{venue.name}</li>
                        </ol>
                    </nav>
                    <h1 className="display-4 fw-bold">{venue.name}</h1>
                    <p className="text-muted fs-5">
                        <i className="bi bi-geo-alt-fill text-primary me-2"></i>
                        Official Partner Venue
                    </p>
                </div>
                <div className="col-md-4">
                    <img
                        src={venue.imageUrl || "https://placehold.co/600x400?text=No+Image"}
                        alt={venue.name}
                        className="img-fluid rounded-4 shadow"
                        style={{height: '250px', width: '100%', objectFit: 'cover'}}
                    />
                </div>
            </div>

            <hr className="my-5"/>

            <div className="row">
                <div className="col-12 mb-4">
                    <h3 className="fw-bold">Upcoming Events at {venue.name}</h3>
                    <p className="text-muted">Check out what's happening at this location.</p>
                </div>

                {venue.events && venue.events.length > 0 ? (
                    venue.events.map(event => (
                        <div key={event.id} className="col-md-6 col-lg-4 mb-4">
                            <div className="card border-0 shadow-sm h-100">
                                <div className="card-body p-4">
                                    <div className="d-flex justify-content-between align-items-start mb-3">
                                        <h5 className="fw-bold mb-0">{event.name}</h5>
                                        <span className="badge bg-primary-subtle text-primary rounded-pill">Live</span>
                                    </div>
                                    <p className="text-muted mb-4">
                                        <i className="bi bi-person-circle me-2"></i>
                                        {event.performer}
                                    </p>
                                    <Link to={`/events/${event.id}`}
                                          className="btn btn-outline-primary w-100 rounded-pill">
                                        View Event details
                                    </Link>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <div className="col-12">
                        <div className="alert alert-light border text-center py-5">
                            <i className="bi bi-calendar-x fs-1 text-muted"></i>
                            <p className="mt-3 mb-0">No events currently scheduled for this venue.</p>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default VenueDetails;