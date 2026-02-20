import axios from 'axios';
import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

const OrderDetails = () => {
    const { number } = useParams();
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {
                const res = await axios.get(`http://localhost:8081/orders/details/${number}`, {
                    headers: { "X-User-Id": 1 }
                });
                setOrder(res.data);
            } catch (err) {
                console.error("Error fetching order:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchOrderDetails();
    }, [number]);


    const handleCancelOrder = async () => {
        try {
            await axios.post(`http://localhost:8081/orders/cancel/${number}`, {}, {
                headers: { "X-User-Id": 1 }
            });
            alert("Order cancelled successfully.");
        } catch (err) {
            alert("Could not cancel order.");
        }
    };

    if (loading) return <div className="container mt-5 text-center text-muted">Loading order details...</div>;
    if (!order) return <div className="container mt-5 text-center text-danger">Order not found.</div>;

    return (
        <div className="container my-5" style={{ maxWidth: '1100px' }}>

            {/* --- TOP SECTION: ORDER OVERVIEW --- */}
            <div className="card border-0 shadow-lg rounded-4 overflow-hidden mb-5">
                <div className="card-header bg-white border-0 p-4 pt-5">
                    <div className="d-flex justify-content-between align-items-start">
                        <div>
                            <Link to="/orders" className="text-decoration-none small fw-bold text-uppercase text-primary mb-2 d-block">
                                ← Back to My Orders
                            </Link>
                            <h2 className="fw-extrabold mb-1">Order #{order.number}</h2>
                            <p className="text-muted mb-0">Placed on {new Date(order.createdAt).toLocaleDateString(undefined, { dateStyle: 'long' })}</p>
                            {order.transactionId === "NOT_APPLICABLE" ? (
                                <span className="badge bg-secondary-subtle text-secondary mt-2">No Transaction Applicable</span>
                            ) : (
                                <span className="badge bg-info-subtle text-info mt-2">Transaction ID: {order.transactionId}</span>
                            )}
                        </div>
                        <span className={`badge rounded-pill px-4 py-2 fs-6 ${order.status === 'COMPLETED' ? 'bg-success-subtle text-success' : 'bg-warning-subtle text-warning-emphasis'
                            }`}>
                            {order.status.replace('_', ' ')}
                        </span>
                    </div>
                </div>

                <div className="card-body p-4 pb-5">
                    <div className="row g-4 text-center text-md-start">
                        <div className="col-md-3 border-end-md">
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Total Amount</label>
                            <h3 className="fw-bold text-success mb-0">${order.totalPrice.toFixed(2)}</h3>
                        </div>
                        <div className="col-md-3 border-end-md">
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Payment Method</label>
                            <h5 className="fw-bold mb-0">Credit Card</h5>
                        </div>
                        <div className="col-md-3 border-end-md">
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Total Items</label>
                            <h5 className="fw-bold mb-0">{order.tickets.length} Tickets</h5>
                        </div>
                        <div className="col-md-3 d-flex align-items-center justify-content-center justify-content-md-end">
                            {order.status === "PENDING_PAYMENT" ? (
                                <button className="btn btn-primary btn-lg rounded-pill px-5 fw-bold shadow">
                                    Complete Payment
                                </button>
                            ) : (
                                <button className="btn btn-outline-dark rounded-pill px-4 fw-bold">
                                    Download Invoice
                                </button>
                            )}

                            <button onClick={handleCancelOrder} className="btn btn-danger btn-lg rounded-pill px-5 fw-bold shadow ms-3">
                                Cancel Order
                            </button>
                        </div>


                    </div>
                </div>
            </div>

            {/* --- BOTTOM SECTION: TICKETS LIST --- */}
            <div className="mb-4">
                <h4 className="fw-bold mb-4">Your Tickets</h4>
                {order.tickets.map((ticket) => (
                    <div key={ticket.id} className="card border-0 shadow-sm rounded-4 p-4 mb-3">
                        <div className="row align-items-center g-4">
                            {/* 1. Event Info */}
                            <div className="col-md-4 border-end-md">
                                <h5 className="fw-bold mb-1 text-primary">{ticket.eventName}</h5>
                                <div className="text-secondary small fw-medium">
                                    {ticket.eventLocationName}
                                </div>
                            </div>

                            {/* 2. Schedule */}
                            <div className="col-md-3 border-end-md">
                                <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Date And Time</label>
                                <h6 className="fw-bold mb-0">
                                    {new Date(ticket.eventStartTime).toLocaleString(undefined, {
                                        weekday: 'short', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit'
                                    })}
                                </h6>
                            </div>
                            {/* 3. Ticket Number */}
                            {/* <div className="col-md-2 border-end-md">
                                <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Ticket ID</label>
                                <code className="fw-bold text-dark fs-6">#{ticket.number}</code>
                            </div> */}

                            {/* 4. Action */}
                            <div className="col-md-3 text-md-end">
                                <button
                                    className="btn btn-light border rounded-pill px-4 py-2 fw-bold w-100"
                                >
                                    <i className="bi bi-download me-2"></i> Download PDF
                                </button>
                            </div>

                            <button className="btn btn-primary btn-lg rounded-pill px-4 fw-bold shadow">
                                Refund Ticket
                            </button>
                        </div>
                    </div>
                ))}
            </div>

        </div>
    );
};

export default OrderDetails;