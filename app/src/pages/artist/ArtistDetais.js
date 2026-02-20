import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import Hero from '../../components/system/Hero';

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
    const availableEvents = artist.locations.filter(loc => loc.eventsLocationsStatus === 'AVAILABLE');
    const unavailableEvents = artist.locations.filter(loc => loc.eventsLocationsStatus !== 'AVAILABLE');

    const PerformanceCard = ({ loc }) => (
        <div className="col-12 mb-3">
            <div className={`card border-0 shadow-sm p-3 ${loc.eventsLocationsStatus !== 'AVAILABLE' ? 'opacity-75 bg-light' : ''}`}>
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
                        {loc.eventsLocationsStatus === 'AVAILABLE' ? (
                            <Link to={`/events/${loc.eventId}`} className="btn btn-primary rounded-pill px-4">
                                Get Tickets
                            </Link>
                        ) : (
                            <button className="btn btn-outline-secondary rounded-pill px-4 disabled" disabled>
                                Sold Out
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
    return (
        //todo: add filtering by date, location, price range, etc.
        <section id="tour-dates" className="py-5">
            <div className="container">
                <Hero
                    badge="🎤 Official Artist Tour"
                    title={artist.name}
                    highlight="on Stage"
                    subtitle={artist.description || `Experience the energy and passion of ${artist.name} live. Browse the tour dates below and join thousands of fans for an unforgettable night.`}
                    primaryAction={{ text: "See All Dates", link: "#tour-dates" }}
                    secondaryAction={{ text: "Back to Events", link: "/events" }}
                />

                {/* <FilterBar 
            filters={filters}
            onFilterChange={(field, value) => setFilters(prev => ({ ...prev, [field]: value }))}
            onReset={() => setFilters({ name: "", category: "", startDate: "", endDate: "", location: "" })}
            showLocation={true}
            showDateRange={true}
            // showName and showCategory stay false for Artist page
        /> */}
                {/* --- PART 1: AVAILABLE TICKETS --- */}
                <div className="mb-4">
                    <h2 className="fw-bold h1">Upcoming Performances</h2>
                    <div className="bg-primary rounded mb-4" style={{ height: '4px', width: '60px' }}></div>
                </div>

                <div className="row mb-5">
                    {availableEvents.length > 0 ? (
                        availableEvents.map(loc => <PerformanceCard key={loc.eventLocationId} loc={loc} />)
                    ) : (
                        <div className="col-12 text-center py-4 border rounded bg-light">
                            <p className="text-muted mb-0">No tickets currently available.</p>
                        </div>
                    )}
                </div>

                {/* --- PART 2: SOLD OUT / UNAVAILABLE --- */}
                {unavailableEvents.length > 0 && (
                    <div className="mt-5">
                        <h3 className="fw-bold text-muted mb-3">Past & Sold Out</h3>
                        <div className="row">
                            {unavailableEvents.map(loc => <PerformanceCard key={loc.eventLocationId} loc={loc} />)}
                        </div>
                    </div>
                )}
            </div>
        </section>
    );
};

export default ArtistDetails;