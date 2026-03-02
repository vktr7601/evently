import { Link } from "react-router-dom";
import { ROUTES } from "../../constants/routes";

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
                        <Link to={ROUTES.ORDERS.ACTIVE} className="btn btn-primary rounded-pill px-4 py-2 fw-bold shadow-sm w-100">
                            Finish Payment
                        </Link>
                    ) : (
                        <Link to={ROUTES.ORDERS.DETAILS(order.number)} className="btn btn-outline-primary rounded-pill px-4 py-2 fw-bold w-100">
                            View Details
                        </Link>
                    )}
                </div>
            </div>
        </div>

    );
}

export default OrderListItem;