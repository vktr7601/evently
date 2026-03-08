import { useState, useEffect, useMemo } from 'react';
import OrderListItem from './OrderListItem';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const ALL_STATUSES = ['ALL', 'CONFIRMED', 'PENDING_PAYMENT', 'CANCELLED', 'EXPIRED', 'REFUNDED'];

const STATUS_LABELS = {
    ALL: 'All',
    CONFIRMED: 'Confirmed',
    PENDING_PAYMENT: 'Pending Payment',
    CANCELLED: 'Cancelled',
    EXPIRED: 'Expired',
    REFUNDED: 'Refunded',
};

const INITIAL_FILTERS = {
    reference: '',
    amountMin: '',
    amountMax: '',
    dateFrom: '',
    dateTo: '',
};

const OrderList = () => {
    const [orders, setOrders] = useState([]);
    const [statusFilter, setStatusFilter] = useState('ALL');
    const [filters, setFilters] = useState(INITIAL_FILTERS);

    useEffect(() => {
        axiosClient.get(ROUTES.ORDERS.BASE)
            .then(res => setOrders(res.data))
            .catch(err => console.error("Error fetching orders:", err));
    }, []);

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const handleReset = () => {
        setFilters(INITIAL_FILTERS);
        setStatusFilter('ALL');
    };

    const filteredOrders = useMemo(() => {
        return orders.filter(o => {
            if (statusFilter !== 'ALL' && o.orderStatus !== statusFilter) return false;

            if (filters.reference && !o.number.toLowerCase().includes(filters.reference.toLowerCase())) return false;

            const amount = o.price ?? 0;
            if (filters.amountMin !== '' && amount < parseFloat(filters.amountMin)) return false;
            if (filters.amountMax !== '' && amount > parseFloat(filters.amountMax)) return false;

            const createdAt = new Date(o.createdAt);
            if (filters.dateFrom && createdAt < new Date(filters.dateFrom)) return false;
            if (filters.dateTo && createdAt > new Date(filters.dateTo + 'T23:59:59')) return false;

            return true;
        });
    }, [orders, statusFilter, filters]);

    const hasActiveFilters = statusFilter !== 'ALL' || Object.values(filters).some(v => v !== '');

    return (
        <div>
            <h1>My Orders</h1>

            <div className="card border-0 shadow-sm rounded-4 p-4 mb-4">
                <div className="row g-3 mb-3">
                    <div className="col-md-4">
                        <label className="form-label small fw-bold text-uppercase text-muted">Reference</label>
                        <input
                            type="text"
                            name="reference"
                            className="form-control rounded-3"
                            placeholder="Search by order #"
                            value={filters.reference}
                            onChange={handleFilterChange}
                        />
                    </div>

                    <div className="col-md-4">
                        <label className="form-label small fw-bold text-uppercase text-muted">Amount Range</label>
                        <div className="d-flex gap-2">
                            <input
                                type="number"
                                name="amountMin"
                                className="form-control rounded-3"
                                placeholder="Min $"
                                min="0"
                                value={filters.amountMin}
                                onChange={handleFilterChange}
                            />
                            <input
                                type="number"
                                name="amountMax"
                                className="form-control rounded-3"
                                placeholder="Max $"
                                min="0"
                                value={filters.amountMax}
                                onChange={handleFilterChange}
                            />
                        </div>
                    </div>

                    <div className="col-md-4">
                        <label className="form-label small fw-bold text-uppercase text-muted">Date Range</label>
                        <div className="d-flex gap-2">
                            <input
                                type="date"
                                name="dateFrom"
                                className="form-control rounded-3"
                                value={filters.dateFrom}
                                onChange={handleFilterChange}
                            />
                            <input
                                type="date"
                                name="dateTo"
                                className="form-control rounded-3"
                                value={filters.dateTo}
                                onChange={handleFilterChange}
                            />
                        </div>
                    </div>
                </div>

                <div className="d-flex flex-wrap align-items-center justify-content-between gap-2">
                    <div className="d-flex flex-wrap gap-2">
                        {ALL_STATUSES.map(status => (
                            <button
                                key={status}
                                onClick={() => setStatusFilter(status)}
                                className={`btn btn-sm rounded-pill px-3 ${statusFilter === status ? 'btn-primary' : 'btn-outline-secondary'}`}
                            >
                                {STATUS_LABELS[status]}
                            </button>
                        ))}
                    </div>

                    {hasActiveFilters && (
                        <button className="btn btn-sm btn-outline-danger rounded-pill px-3" onClick={handleReset}>
                            Clear Filters
                        </button>
                    )}
                </div>
            </div>

            {filteredOrders.length === 0 ? (
                <p className="text-muted">No orders found.</p>
            ) : (
                <div className="row">
                    {filteredOrders.map(order => (
                        <div key={order.number} className="col-12 mb-3">
                            <OrderListItem order={order} />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default OrderList;