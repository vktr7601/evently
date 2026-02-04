import {useEffect, useState} from "react";
import axios from 'axios';
import {Link} from "react-router-dom";

const Venues = () => {
    const [venues, setVenues] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get("http://localhost:9000/venues")
            .then(res => {
                const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
                setVenues(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching venues:", err);
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
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <div>
                    <h2 className="fw-bold text-dark mb-0">Partner Venues</h2>
                    <p className="text-muted mb-0">Explore world-class locations</p>
                </div>
                <span className="badge bg-primary rounded-pill px-3 py-2">{venues.length} Locations</span>
            </div>

            {venues.length === 0 ? (
                <div className="alert alert-light text-center py-5 border">
                    <i className="bi bi-geo-alt fs-1 text-muted"></i>
                    <p className="mt-3 text-muted">No venues found at the moment.</p>
                </div>
            ) : (
                <div className="row">
                    {venues.map(venue => (
                        <div key={venue.id} className="col-12 col-md-6 col-lg-4 mb-4">
                            <VenueItem venue={venue}/>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

const VenueItem = ({venue}) => {
    const hasImage = venue.imageUrl && venue.imageUrl.trim() !== "";

    return (
        <div className="card h-100 shadow-sm border-0 transition-hover">
            {hasImage ? (
                <img
                    src={venue.imageUrl}
                    className="card-img-top"
                    alt={venue.name}
                    style={{height: '200px', objectFit: 'cover'}}
                    onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = "https://placehold.co/600x400?text=Venue+Image";
                    }}
                />
            ) : (
                <div
                    className="card-img-top"
                    style={{
                        height: '200px',
                        background: 'linear-gradient(135deg, #6610f2, #6f42c1)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center'
                    }}
                >
                    <i className="bi bi-geo-alt-fill text-white fs-1"></i>
                </div>
            )}

            <div className="card-body d-flex flex-column p-4">
                <h5 className="card-title fw-bold mb-1">{venue.name}</h5>

                <p className="text-muted small mb-3">
                    <i className="bi bi-geo-alt me-2 text-primary"></i>
                    {venue.location || 'Address not listed'}
                </p>

                <div className="mt-auto pt-3 border-top d-flex align-items-center justify-content-between">
                    <span className="text-dark fw-bold small">
                        <i className="bi bi-people me-1"></i> {venue.capacity || 'N/A'}
                    </span>
                    <Link
                        to={`/venues/${venue.id}`}
                        className="btn btn-sm btn-primary rounded-pill px-4"
                    >
                        Explore
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Venues;