import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

const ArtistDetails = () => {
    const isAuth = true;
    const { id } = useParams();
    const [artist, setArtist] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8082/artists/${id}`)
            .then(res => {
                setArtist(res.data);
            })
            .catch(err => {
                console.error(err);
            });
    }, [id]);
    if (!artist) return <div className="text-center py-5 mt-5"><div className="spinner-border text-primary"></div></div>;
    return (

        <div className="bg-white min-vh-100">
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-4 mb-4 mb-lg-0">
                            <img
                                src={artist.imageUrl}
                                alt={artist.name}
                                className="img-fluid rounded-4 shadow-lg w-100"
                                style={{ objectFit: 'cover', height: '400px' }}
                            />
                        </div>
                        <div className="col-lg-8 ps-lg-5">
                            <h6 className="text-primary fw-bold text-uppercase tracking-wider">Featured Artist</h6>

                            <p className="fs-5 text-secondary mb-4" style={{ lineHeight: '1.8' }}>
                                {artist.bio}
                            </p>
                            <div className="d-flex gap-3">
                                <a href="#tour-dates" className="btn btn-primary btn-lg rounded-pill px-5">View Tour Dates</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="tour-dates" className="py-5">
                <div className="container">
                    <div className="mb-5">
                        <h2 className="fw-bold h1">Upcoming Performances</h2>
                        <div className="bg-primary rounded" style={{ height: '4px', width: '60px' }}></div>
                    </div>

                    <div className="row g-4">
                        {artist.locations && artist.locations.length > 0 ? (
                            artist.locations.map((loc) => (
                                <div key={loc.eventLocationId} className="col-12">
                                    <div className={`card border-0 shadow-sm p-3 transition-hover ${loc.status === 'SOLD_OUT' ? 'opacity-75' : ''}`}>
                                        <div className="row align-items-center text-center text-md-start">
                                            <div className="col-md-2 border-end-md">
                                                <h4 className="fw-bold mb-0">
                                                    {new Date(loc.eventStartTime).toLocaleDateString('en-US', { day: '2-digit', month: 'short' })}
                                                </h4>
                                                <small className="text-muted">
                                                    {new Date(loc.eventStartTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                                </small>
                                            </div>

                                            <div className="col-md-5">
                                                <h5 className="fw-bold mb-1">{loc.eventName}</h5>
                                                <p className="text-primary mb-0">
                                                    <i className="bi bi-geo-alt-fill me-1"></i>
                                                    {loc.locationName}
                                                </p>
                                            </div>

                                            <div className="col-md-2">
                                                <span className="fs-5 fw-bold text-dark">€{loc.pricePerTicket.toFixed(2)}</span>
                                            </div>

                                            <div className="col-md-3 text-md-end mt-3 mt-md-0">
                                                {loc.status === 'AVAILABLE' ? (
                                                    <Link to={`/events/${loc.eventId}`} className="btn btn-primary rounded-pill px-4">
                                                        Get Tickets
                                                    </Link>
                                                ) : (
                                                    <button className="btn btn-secondary rounded-pill px-4 disabled" disabled>
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
                                <p className="text-muted">No upcoming shows found for this artist.</p>
                            </div>
                        )}
                    </div>
                </div>
            </section>
        </div>
    );
};

export default ArtistDetails;