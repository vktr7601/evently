import { Link } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';
const EventLocationListItem = ({ loc }) => {
    const renderBookingButton = (loc) => {
        switch (loc.eventsLocationsStatus) {
            case 'AVAILABLE':
                return (
                    <Link
                        to={ROUTES.EVENTS.EVENT_LOCATIONS_DETAILS(loc.id)}
                        className="btn btn-primary rounded-pill px-4"
                    >
                        Book Tickets
                    </Link>
                );
            case 'CANCELLED':
                return (
                    <button className="btn btn-outline-danger rounded-pill px-4 disabled" disabled>
                        Cancelled
                    </button>
                );
            case 'PENDING_TICKETS':
                return (
                    <button className="btn btn-warning rounded-pill px-4 disabled" disabled>
                        Coming Soon
                    </button>
                );
            default:
                return (
                    <button className="btn btn-secondary rounded-pill px-4 disabled" disabled>
                        Sold Out
                    </button>
                );
        }
    };

    return (
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
                        <span className="fs-5 fw-bold text-dark">
                            ${typeof loc.pricePerTicket === 'number' ? loc.pricePerTicket.toFixed(2) : '0.00'}
                        </span>
                    </div>

                    <div className="col-md-3 text-md-end mt-3 mt-md-0">
                        {renderBookingButton(loc)}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EventLocationListItem;