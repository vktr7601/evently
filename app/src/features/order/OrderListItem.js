import { Link } from "react-router-dom";

const OrderListItem = ({ order }) => {

    return (
        <div key={order.number} className="card border-0 shadow-sm rounded-4 p-4 mb-3 overflow-hidden">
            <div className="row align-items-center g-4">

                <div className="col-md-2 border-end-md">
                    <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Order Date</label>
                    <h5 className="fw-bold mb-0">
                        {new Date(order.createdAt).toLocaleDateString('en-US', {
                            day: '2-digit',
                            month: 'short',
                            year: 'numeric'
                        })}
                    </h5>
                </div>


                <div className="col-md-3 border-end-md">
                    <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Reference</label>
                    <h5 className="fw-bold mb-0 text-dark">#{order.number}</h5>
                </div>


                <div className="col-md-2 border-end-md">
                    <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Amount</label>
                    <h4 className="fw-bold mb-0 text-success">
                        {order.orderStatus !== 'PENDING_PAYMENT' ? (
                            `$${order.price.toFixed(2)}`
                        ) : (
                            <span className="text-muted fst-italic small">Calculated at checkout</span>
                        )}
                    </h4>
                </div>

                <div className="col-md-2">
                    <label className="text-muted small fw-bold d-block mb-1 text-uppercase">Status</label>
                    <span className={`badge rounded-pill px-3 py-2 ${order.orderStatus === 'COMPLETED' ? 'bg-success-subtle text-success' :
                        order.orderStatus === 'PENDING_PAYMENT' ? 'bg-warning-subtle text-warning-emphasis' :
                            'bg-light text-secondary'
                        }`}>
                        {order.orderStatus.replace('_', ' ')}
                    </span>
                </div>

                <div className="col-md-3 text-md-end">
                    {order.orderStatus === 'PENDING_PAYMENT' ? (
                        <Link to="/order/active" className="btn btn-primary rounded-pill px-4 py-2 fw-bold shadow-sm w-100">
                            Finish Payment
                        </Link>
                    ) : (
                        <Link to={`/orders/details/${order.number}`} className="btn btn-outline-primary rounded-pill px-4 py-2 fw-bold w-100">
                            View Details
                        </Link>
                    )}
                </div>
            </div>
        </div>

    );
}

export default OrderListItem;

// const getStatusStyles = (status) => {
//     switch (status) {
//         case 'COMPLETED': return { backgroundColor: '#ecfdf5', color: '#059669' }; // Green
//         case 'CANCELLED': return { backgroundColor: '#fef2f2', color: '#dc2626' }; // Red
//         case 'PENDING': return { backgroundColor: '#eff6ff', color: '#2563eb' };   // Blue
//         default: return { backgroundColor: '#f9fafb', color: '#6b7280' };
//     }
// };

// const styles = {
//     orderRow: {
//         backgroundColor: '#fff',
//         borderRadius: '16px',
//         padding: '20px 24px',
//         marginBottom: '12px',
//         border: '1px solid #e2e8f0',
//         transition: 'transform 0.2s ease',
//         boxShadow: '0 1px 3px rgba(0,0,0,0.02)'
//     },
//     orderGrid: {
//         display: 'flex',
//         alignItems: 'center',
//         justifyContent: 'space-between',
//         gap: '20px',
//         flexWrap: 'wrap'
//     },
//     metaLabel: {
//         display: 'block',
//         fontSize: '11px',
//         fontWeight: '700',
//         color: '#94a3b8',
//         textTransform: 'uppercase',
//         letterSpacing: '0.05em',
//         marginBottom: '4px'
//     },
//     dateText: { fontSize: '15px', fontWeight: '500', color: '#1e293b' },
//     orderNumber: { fontSize: '16px', fontWeight: '700', color: '#1e293b' },
//     priceText: { fontSize: '16px', fontWeight: '800', color: '#0f172a' },
//     statusBadge: {
//         padding: '6px 12px',
//         borderRadius: '8px',
//         fontSize: '12px',
//         fontWeight: '700',
//         display: 'inline-block'
//     },
//     detailsBtn: {
//         backgroundColor: 'transparent',
//         border: '1px solid #e2e8f0',
//         padding: '8px 16px',
//         borderRadius: '10px',
//         fontSize: '13px',
//         fontWeight: '600',
//         color: '#475569',
//         cursor: 'pointer',
//         transition: 'all 0.2s ease'
//     },
//     // Flex basis for alignment
//     dateInfo: { flex: '1 1 120px' },
//     mainInfo: { flex: '1 1 150px' },
//     amountInfo: { flex: '1 1 100px' },
//     statusInfo: { flex: '1 1 100px', textAlign: 'center' },
//     actionInfo: { flex: '0 0 auto' }
// };