import { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const LocationDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    // State management aligned with EventDetails style
    const [occurrence, setOccurrence] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // User interaction state
    const [quantity, setQuantity] = useState(1);
    const [promoCode, setPromoCode] = useState("");

    useEffect(() => {
        const fetchDetails = async () => {
            try {
                // Fetching from your event location endpoint
                const response = await axios.get(`http://localhost:8082/events/${id}/location`);
                setOccurrence(response.data);
            } catch (err) {
                setError('Failed to load event location details.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchDetails();
    }, [id]);

    const handleBooking = async () => {
        setError(null);
        try {
            // 1. Check Availability
            const availRes = await axios.get(`http://localhost:8081/tickets/availability`, {
                params: {
                    eventLocationId: occurrence.eventLocationId,
                    ticketsCount: quantity
                }
            });

            // 2. Create Order (if 204 No Content)
            if (availRes.status === 204) {
                const orderRes = await axios.post("http://localhost:8081/orders", {
                    event_location_id: occurrence.eventLocationId,
                    tickets_count: quantity,
                    date_time: occurrence.eventStartTime,
                    promo_code: promoCode
                }, {
                    // This is your Config Object
                    headers: {
                        'X-User-Id': `1`,
                    }
                });

                // 3. Navigate to Payment
                navigate(`/order/active/`);
            }
        } catch (err) {
            const status = err.response?.status;
            if (status === 400) setError('Insufficient tickets available.');
            else if (status === 404) setError('Event or tickets not found.');
            else setError('Service unavailable. Please try again later.');
        }
    };

    if (loading) return (
        <div className="text-center py-5 mt-5">
            <div className="spinner-border text-primary"></div>
        </div>
    );

    const isAvailable = occurrence?.eventsLocationsStatus === 'AVAILABLE';

    return (
        <div className="bg-white min-vh-100">
            {/* Hero Section - Matching EventDetails style */}
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <div className="mb-2">
                        <span className="badge bg-primary-subtle text-primary px-3 py-2 text-uppercase fw-bold small">
                            {isAvailable ? 'Tickets Available' : 'Sold Out'}
                        </span>
                    </div>
                    <h1 className="display-4 fw-black text-dark mb-2">{occurrence.eventName}</h1>
                    <p className="fs-5 text-primary fw-bold">
                        <i className="bi bi-geo-alt-fill me-2"></i>
                        {occurrence.locationName}
                    </p>
                </div>
            </section>

            {/* Booking Action Card */}
            <section className="py-5">
                <div className="container">
                    {error && <div className="alert alert-danger mb-4 rounded-3">{error}</div>}

                    <div className="card border-0 shadow-lg rounded-4 p-4 overflow-hidden">
                        <div className="row align-items-center g-4">
                            {/* Date Column */}
                            <div className="col-md-4 border-end-md">
                                <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Date & Time</label>
                                <h4 className="fw-bold mb-0">
                                    {new Date(occurrence.eventStartTime).toLocaleString(undefined, {
                                        weekday: 'short', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit'
                                    })}
                                </h4>
                            </div>

                            {/* Price Column */}
                            <div className="col-md-3 border-end-md">
                                <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Price</label>
                                <h4 className="fw-bold mb-0 text-success">
                                    ${occurrence.pricePerTicket.toFixed(2)}
                                </h4>
                            </div>

                            {/* Quantity Selection */}
                            <div className="col-md-2">
                                <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Quantity</label>
                                <select
                                    className="form-select border-0 bg-light fw-bold py-2"
                                    value={quantity}
                                    onChange={(e) => setQuantity(parseInt(e.target.value))}
                                    disabled={!isAvailable}
                                >
                                    {[1, 2, 3, 4, 5, 6].map(num => (
                                        <option key={num} value={num}>{num} Tickets</option>
                                    ))}
                                </select>
                            </div>

                            {/* CTA Button */}
                            <div className="col-md-3 text-md-end">
                                <button
                                    onClick={handleBooking}
                                    disabled={!isAvailable}
                                    className={`btn btn-lg rounded-pill px-5 py-3 fw-bold transition-all w-100 ${isAvailable ? 'btn-primary shadow' : 'btn-secondary opacity-50'
                                        }`}
                                >
                                    {isAvailable ? 'Book Now' : 'Sold Out'}
                                </button>
                            </div>
                        </div>
                    </div>

                    {/* Promo Code & Extra Info */}
                    <div className="row mt-5 g-4">
                        <div className="col-lg-8">
                            <h4 className="fw-bold mb-3">Venue Information</h4>
                            <p className="text-secondary fs-5" style={{ lineHeight: '1.8' }}>
                                This event is hosted at <strong>{occurrence.locationName}</strong>.
                                We recommend arriving at least 30 minutes before the scheduled start time
                                of {new Date(occurrence.eventStartTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                to ensure a smooth entry.
                            </p>
                        </div>

                        <div className="col-lg-4">
                            <div className="p-4 bg-light rounded-4 border">
                                <h6 className="fw-bold mb-3">Have a promo code?</h6>
                                <div className="d-flex gap-2">
                                    <input
                                        type="text"
                                        className="form-control border-white shadow-sm"
                                        placeholder="GIFT2026"
                                        value={promoCode}
                                        onChange={(e) => setPromoCode(e.target.value.toUpperCase())}
                                    />
                                    <button className="btn btn-dark rounded-3 px-3">Apply</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default LocationDetails;