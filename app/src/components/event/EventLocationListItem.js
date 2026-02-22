import { Link } from 'react-router-dom';

const EventLocationListItem = ({ loc }) => (
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
                        <button className="btn btn-outline-secondary rounded-pill px-4" disabled>
                            Sold Out
                        </button>
                    )}
                </div>
            </div>
        </div>
    </div>
);

export default EventLocationListItem;
