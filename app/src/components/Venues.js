import { useEffect, useState } from "react";
import axios from 'axios';
import { Link } from "react-router-dom";

const Venues = () => {
    const [venues, setVenues] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get("http://localhost:8082/venues")
            .then(res => {
                setVenues(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching venues:", err);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return (
            <div className="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center bg-white" style={{ zIndex: 9999, opacity: 0.8 }} >
                <div className="spinner-border text-primary" role="status" style={{ width: '4rem', height: '4rem' }}>
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        );
    }

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <div>
                    <div>
                        <h2 className="fw-bold text-dark mb-0">Explore Places</h2>
                    </div>
                </div>
            </div>

            <div className="row">
                {venues.map(venue => (
                    <div key={venue.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <VenueItem venue={venue} />
                    </div>
                ))}
            </div>
        </div>
    );
}

const VenueItem = ({ venue }) => {

    return (
        <div className="card h-100 shadow-sm border-0 transition-hover overflow-hidden">
            <div className="position-relative">
                <img
                    src={venue.imageUrl}
                    className="card-img-top"
                    alt={venue.name}
                    style={{ height: '220px', objectFit: 'cover' }}
                    onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = "https://placehold.co/600x400?text=Venue+Image";
                    }}
                />
            </div>

            <div className="card-body d-flex flex-column p-4">
                <h5 className="card-title fw-bold text-dark mb-2">{venue.name}</h5>

                <p className="card-text text-muted small line-clamp-2 mb-4">
                    {venue.description || 'No description available for this world-class location.'}
                </p>

                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-between">
                    <div className="d-flex align-items-center text-primary">
                        <i className="bi bi-geo-alt me-1"></i>
                        <span className="small fw-semibold">Bulgaria</span>
                    </div>
                    <Link to={`/venues/${venue.id}`}className="btn btn-sm btn-dark rounded-pill px-4">
                        Expore Events
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Venues;