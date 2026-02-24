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
    const [showMaintenanceMode, setShowMaintenanceMode] = useState(false);

    // User interaction state
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        const fetchDetails = async () => {
            try {
                // Fetching from your event location endpoint
                const response = await axios.get(`http://localhost:8082/events/${id}/location`);
                setOccurrence(response.data);
                console.log("Fetched event location details:", response.data);
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
                    eventLocationId: occurrence.id,
                    ticketsCount: quantity
                }
            });


            try {
                const orderRes = await axios.post("http://localhost:8081/orders", {
                    eventLocationId: occurrence.id,
                    ticketsCount: quantity,
                    eventStartTime: occurrence.eventStartTime
                }, {
                    headers: { 'X-User-Id': 1 }
                });

                // 1. Validate the creation was successful (201 Created)
                if (orderRes.status === 201 || orderRes.status === 204) {

                    // 2. Sync your localStorage immediately
                    console.log("Order created successfully:", orderRes.data);

                    navigate(`/order/active`);
                }
            } catch (err) {
                // 4. Handle specific backend errors (e.g., "User already has an active order")
                const errorMessage = err.response?.data?.message || "Could not create order";
                alert(errorMessage);
            }
        } catch (err) {
            console.error("Data:", err.response.data);
            console.error("Status:", err.response.status);
            // const status = err.response?.status;
            if (err.response?.status === 400) setError('Insufficient tickets available.');
            if (err.response?.status === 409) {
                setError("Insufficient tickets available. This event may have just sold out. Please try again.");
                setShowMaintenanceMode(true);
            }
            else if (err.response?.status === 404) setError('Event or tickets not found.');
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
                                <div className="d-flex align-items-center bg-light rounded px-2 py-1">
                                    <button
                                        className="btn btn-sm btn-outline-secondary border-0 px-2"
                                        onClick={() => setQuantity(q => Math.max(1, q - 1))}
                                        disabled={!isAvailable || quantity <= 1}
                                    >−</button>
                                    <span className="fw-bold mx-3">{quantity}</span>
                                    <button
                                        className="btn btn-sm btn-outline-secondary border-0 px-2"
                                        onClick={() => setQuantity(q => Math.min(8, q + 1))}
                                        disabled={!isAvailable}
                                    >+</button>
                                </div>
                            </div>

                            {/* CTA Button */}
                            <div className="col-md-3 text-md-end">

                                {showMaintenanceMode ? (
                                    <button
                                        disabled
                                        className="btn btn-danger rounded-pill px-5 py-2 shadow-sm"
                                        style={{
                                            opacity: 1,           // Prevents the "faded" look
                                            backgroundColor: '#dc3545', // Standard Bootstrap Red
                                            borderColor: '#dc3545',
                                            cursor: 'not-allowed' // Shows a "prohibited" icon on hover
                                        }}
                                    >

                                        Temporarily Unavailable
                                    </button>

                                ) : (
                                    <button
                                        onClick={handleBooking}
                                        disabled={!isAvailable}
                                        className={`btn btn-lg rounded-pill px-5 py-3 fw-bold transition-all w-100 ${isAvailable ? 'btn-primary shadow' : 'btn-secondary opacity-50'
                                            }`}
                                    >
                                        {isAvailable ? 'Book Now' : 'Sold Out'}
                                    </button>
                                )}
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
                    </div>
                </div>
            </section>
        </div>
    );
};

export default LocationDetails; 