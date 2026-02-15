import { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const LocationDetails = () => {
    const { id } = useParams();
    const [formData, setFormData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [promoCode, setPromoCode] = useState("");
    const [quantity, setQuantity] = useState(1); // Default to 1 ticketÍ
    useEffect(() => {
        const fetchLocationDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8082/events/${id}/location`);
                setFormData(response.data);
            } catch (err) {
                setError('Failed to load event location details.');
            } finally {
                setLoading(false);
            }
        };
        fetchLocationDetails();
    }, [id]);

    const handleBooking = () => {
    //   axios.get(`http://localhost:8081/events/${id}/location`)
    //     .then(res => {
    //       const eventLocation = res.data;
    //     // Here you would typically send this data to your backend to create a booking
    }


    // if (loading) return <div className="text-center py-5 mt-5"><div className="spinner-border text-primary"></div></div>;
    // if (error) return <div className="alert alert-danger text-center m-5">{error}</div>;

    return (
        <div className="bg-white min-vh-100">
            {/* Header Section */}
            <section className="py-5 bg-light border-bottom">
                <div className="container">
                    <h1 className="display-5 fw-black text-dark mb-2">{formData.eventName}</h1>
                    <p className="fs-5 text-primary fw-bold">
                        <i className="bi bi-geo-alt-fill me-2"></i>
                        {formData.locationName}
                    </p>
                </div>
            </section>

            {/* Event Info Card */}
            <section className="py-5">
                <div className="container">
                    <div className="card border-0 shadow-sm rounded-4 p-4">
                        <div className="row align-items-center">
                            <div className="col-md-4 border-end-md">
                                <label className="text-muted small fw-bold d-block mb-1">DATE & TIME</label>
                                <p className="h5 fw-bold mb-0">
                                    {new Date(formData.eventStartTime).toLocaleString(undefined, {
                                        weekday: 'short', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit'
                                    })}
                                </p>
                            </div>
                            <div className="col-md-3 border-end-md mt-3 mt-md-0">
                                <label className="text-muted small fw-bold d-block mb-1">PRICE</label>
                                <p className="h5 fw-bold mb-0 text-success">${formData.pricePerTicket.toFixed(2)}</p>
                            </div>
                            <div className="col-md-2 mt-3 mt-md-0">
                                <label className="text-muted small fw-bold d-block mb-1">QUANTITY</label>
                                <select
                                    className="form-select border-0 bg-light fw-bold"
                                    value={quantity}
                                    onChange={(e) => setQuantity(e.target.value)}
                                    disabled={formData.status !== 'AVAILABLE'}
                                >
                                    {[1, 2, 3, 4, 5, 6].map(num => (
                                        <option key={num} value={num}>{num}</option>
                                    ))}
                                </select>
                            </div>
                            <div className="col-md-3 text-md-end mt-4 mt-md-0">
                                <button
                                    onClick={handleBooking}
                                    disabled={formData.status !== 'AVAILABLE'}
                                    className={`btn btn-lg rounded-pill px-5 fw-bold ${formData.status === 'AVAILABLE'
                                            ? 'btn-primary shadow'
                                            : 'btn-secondary opacity-50'
                                        }`}
                                >
                                    {formData.status === 'AVAILABLE' ? 'Book Now' : 'Sold Out'}
                                </button>
                            </div>
                        </div>
                    </div>

                    {/* Just for fun: a small descriptive section */}
                    <div className="mt-5">
                        <h4 className="fw-bold">About this location</h4>
                        <p className="text-secondary">
                            Join us at the <strong>{formData.locationName}</strong> for an unforgettable experience with <strong>{formData.eventName}</strong>.
                            Please arrive 30 minutes before the start time.
                        </p>
                    </div>
                </div>

                <div className="mt-3">

                    <div className="d-flex align-items-center animate__animated animate__fadeIn" style={{ maxWidth: '300px' }}>
                        <input
                            type="text"
                            className="form-control form-control-sm border-0 bg-light me-2"
                            placeholder="Enter code"
                            value={promoCode}
                            onChange={(e) => setPromoCode(e.target.value.toUpperCase())}
                        />
                        <button
                            className="btn btn-sm btn-outline-secondary rounded-pill"
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default LocationDetails;