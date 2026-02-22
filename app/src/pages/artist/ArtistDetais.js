import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import EventLocationListItem from '../../components/event/EventLocationListItem';

const ArtistDetails = () => {
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
    const availableEvents = artist.eventLocations.filter(loc => loc.eventsLocationsStatus === 'AVAILABLE');
    const unavailableEvents = artist.eventLocations.filter(loc => loc.eventsLocationsStatus !== 'AVAILABLE');

    return (
        <div className="bg-white min-vh-100">
            {/* Hero Section: Artist Info */}
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-4 mb-4 mb-lg-0">
                            {artist.imageUrl ? (
                                <img
                                    src={artist.imageUrl}
                                    alt={artist.name}
                                    className="img-fluid rounded-4 shadow-lg w-100"
                                    style={{ objectFit: 'cover', height: '400px' }}
                                />
                            ) : (
                                <div
                                    className="rounded-4 shadow-lg w-100 bg-primary-subtle d-flex align-items-center justify-content-center"
                                    style={{ height: '400px' }}
                                >
                                    <i className="bi bi-person-fill text-primary" style={{ fontSize: '96px' }}></i>
                                </div>
                            )}
                        </div>
                        <div className="col-lg-8 ps-lg-5">
                            <span className="badge rounded-pill bg-primary-subtle text-primary px-3 py-2 text-uppercase fw-bold small mb-3 d-inline-block">
                                🎤 Official Artist Tour
                            </span>
                            <h1 className="display-3 fw-black text-dark mb-3">{artist.name}</h1>
                            <p className="fs-5 text-secondary mb-4" style={{ lineHeight: '1.8' }}>
                                {artist.description || `Experience the energy and passion of ${artist.name} live. Browse the tour dates below and join thousands of fans for an unforgettable night.`}
                            </p>
                            <div className="d-flex gap-3 mt-4">
                                <a href="#tour-dates" className="btn btn-primary btn-lg rounded-pill px-5">
                                    See All Dates
                                </a>
                                <Link to="/events" className="btn btn-outline-secondary btn-lg rounded-pill px-5">
                                    Back to Events
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="tour-dates" className="py-5">
                <div className="container">
                <div className="mb-4">
                    <h2 className="fw-bold h1">Upcoming Performances</h2>
                    <div className="bg-primary rounded mb-4" style={{ height: '4px', width: '60px' }}></div>
                </div>

                <div className="row mb-5">
                    {availableEvents.length > 0 ? (
                        availableEvents.map(loc => <EventLocationListItem key={loc.eventLocationId} loc={loc} />)
                    ) : (
                        <div className="col-12 text-center py-4 border rounded bg-light">
                            <p className="text-muted mb-0">No tickets currently available.</p>
                        </div>
                    )}
                </div>

                {unavailableEvents.length > 0 && (
                    <div className="mt-5">
                        <h3 className="fw-bold text-muted mb-3">Past & Sold Out</h3>
                        <div className="row">
                            {unavailableEvents.map(loc => <EventLocationListItem key={loc.eventLocationId} loc={loc} />)}
                        </div>
                    </div>
                )}
                </div>
            </section>
        </div>
    );
};

export default ArtistDetails;