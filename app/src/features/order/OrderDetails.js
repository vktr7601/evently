import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';
import TicketListItem from '../ticket/TicketListItem';

const OrderDetails = () => {
    const { number } = useParams();
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {

                const res = await axiosClient.get(ROUTES.ORDERS.DETAILS(number));
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

    const checkIsOrderRefundable = () => {
        const refundableStatuses = ['COMPLETED', 'CONFIRMED'];
        return refundableStatuses.includes(order.status);
    };

    const checkIfTicketIsRefundable = (ticket) => {
        const refundableStatuses = ['COMPLETED', 'CONFIRMED'];
        return refundableStatuses.includes(order.status) && !ticket.isRefunded;
    }

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
                        <Link to={`/payment-transactions/${order.transactionId}`} className="btn btn-outline-dark rounded-pill px-4 fw-bold">
                            View Transaction
                        </Link>
                        {/* <a href={order.receiptUrl} target="_blank" rel="noreferrer" className="btn btn-outline-dark rounded-pill px-4 fw-bold">
                            View Receipt
                        </a> */}
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
    if (loading) return <div className="container mt-5 text-center text-muted">Loading order details...</div>;
    if (!order) return <div className="container mt-5 text-center text-danger">Order not found.</div>;

    return (
        <div className="container my-5" style={{ maxWidth: '1100px' }}>

            <div className="card border-0 shadow-lg rounded-4 overflow-hidden mb-5">
                <div className="card-header bg-white border-0 p-4 pt-5">
                    <div className="d-flex justify-content-between align-items-start">
                        <div>
                            <Link to="/orders" className="text-decoration-none small fw-bold text-uppercase text-primary mb-2 d-block">
                                ← Back to My Orders
                            </Link>
                            <h2 className="fw-extrabold mb-1">Order #{order.number}</h2>
                            <p className="text-muted mb-0">Placed on {new Date(order.createdAt).toLocaleDateString(undefined, { dateStyle: 'long' })}</p>
                            {order.transactionId === "NOT_APPLICABLE" && (
                                <span className="badge bg-secondary-subtle text-secondary mt-2">No Transaction Applicable</span>
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
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Total Items</label>
                            <h5 className="fw-bold mb-0">{order.tickets.length} Tickets</h5>
                        </div>
                        <div className="col-md-6 d-flex justify-content-md-end align-items-center">
                            {renderActionButtons()}
                        </div>
                        {order.status === 'CONFIRMED' && (
                            <button
                                className="btn btn-outline-danger btn-lg rounded-pill px-4 fw-bold w-100"
                            >
                                Refund Order
                            </button>
                        )}
                    </div>
                </div>

            </div >

            <div className="mb-4">
                <h4 className="fw-bold mb-4">Tickets</h4>
                {order.tickets.map((ticket) => (
                    <TicketListItem
                        key={ticket.id}
                        ticket={ticket}

                    />
                ))}
            </div>
        </div >
    );
};

export default OrderDetails;