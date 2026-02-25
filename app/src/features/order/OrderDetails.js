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
                const res = await axios.get(`http://localhost:9000/orders/details/${number}`, {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem("jwtToken")}`,
                    }
                });
                console.log("Fetched order details:", res.data);
                setOrder(res.data);
            } catch (err) {
                console.error("Error fetching order:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchOrderDetails();
    }, [number]);

    const renderActionButtons = () => {
        switch (order.status) {
            case 'PENDING_PAYMENT':
                return (
                    <Link to="/checkout" className="btn btn-primary btn-lg rounded-pill px-5 fw-bold shadow">
                        Complete Payment
                    </Link>
                );
            case 'CONFIRMED':
                return (
                    <div className="d-flex gap-2">
                        <a href={order.receiptUrl} target="_blank" rel="noreferrer" className="btn btn-outline-dark rounded-pill px-4 fw-bold">
                            View Receipt
                        </a>
                        <button onClick={handleRefundRequest} className="btn btn-success rounded-pill px-4 fw-bold">
                            Request Refund
                        </button>
                        {/* {isEligibleForRefund && (
                            <button onClick={handleRefundRequest} className="btn btn-warning rounded-pill px-4 fw-bold">
                                Request Refund
                            </button> */}
                        )}
                    </div>
                );
            case 'CANCELLED':
                return <span className="text-muted fw-bold">This order was cancelled. No further actions possible</span>;
            case 'REFUNDED':
                return <span className="text-muted fw-bold">This order was returned.</span>;
            default:
                return null;
        };
    };

    const handleRefundRequest = async () => {
        try {
            await axios.post(`http://localhost:9000/orders/refund/${number}`, {}, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("jwtToken")}`,
                }
            });
            alert("Refund requested successfully.");
        } catch (err) {
            alert("Could not request refund.");
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
                        <div className="col-md-6 d-flex justify-content-md-end align-items-center">
                            {renderActionButtons()}
                        </div>
                    </div>
                </div>

            </div >

            {/* --- BOTTOM SECTION: TICKETS LIST --- */}
            <div className="mb-4">
                <h4 className="fw-bold mb-4">Tickets</h4>
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

                            {/* 3. Download Action */}
                            <div className="col-md-2 text-md-end">
                                {ticket.status !== 'CANCELED' && (
                                    <button className="btn btn-light border rounded-pill px-4 py-2 fw-bold w-100">
                                        <i className="bi bi-download me-2"></i> PDF
                                    </button>
                                )}
                            </div>

                            {/* 4. Conditional Refund Button or Status Badge */}
                            <div className="col-md-3 text-md-end">
                                {ticket.status === 'CANCELED' ? (
                                    <span className="badge bg-danger-subtle text-danger rounded-pill px-4 py-2 fs-6">
                                        CANCELED
                                    </span>
                                ) : (
                                    <button
                                        className="btn btn-primary btn-lg rounded-pill px-4 fw-bold shadow w-100"
                                        onClick={() => console.log("Refund specific ticket:", ticket.id)}
                                    >
                                        Refund Ticket
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div >
    );
};

export default OrderDetails;