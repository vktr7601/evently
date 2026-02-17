import { Link } from "react-router-dom";

const OrderListItem = ({ order }) => {

    return (
        <div key={order.number} style={styles.orderRow}>
            <div style={styles.orderGrid}>
                <div style={styles.dateInfo}>
                    <span style={styles.metaLabel}>Date</span>
                    <div style={styles.dateText}>
                        {new Date(order.createdAt).toLocaleDateString('en-US', {
                            day: '2-digit',
                            month: 'short',
                            year: 'numeric'
                        })}
                    </div>
                </div>

                {/* 2. Order Number */}
                <div style={styles.mainInfo}>
                    <span style={styles.metaLabel}>Order Reference</span>
                    <div style={styles.orderNumber}>#{order.number}</div>
                </div>

                {/* 3. Total Price */}
                <div style={styles.amountInfo}>
                    <span style={styles.metaLabel}>Amount</span>
                    <div style={styles.priceText}>${order.totalPrice.toFixed(2)}</div>
                </div>

                {/* 4. Status Badge */}
                <div style={styles.statusInfo}>
                    <span style={{
                        ...styles.statusBadge,
                        ...getStatusStyles(order.orderStatus)
                    }}>
                        {order.orderStatus}
                        {order.orderStatus === 'PENDING' && (
                            <div>Finish The payment</div>
                        )}
                    </span>
                </div>

                <Link to={`/orders/details/${order.number}`} className="btn btn-primary rounded-pill px-5 py-2 shadow-sm">
                    View Details
                </Link>
            </div>
        </div>
    );
}

export default OrderListItem;

const getStatusStyles = (status) => {
    switch (status) {
        case 'COMPLETED': return { backgroundColor: '#ecfdf5', color: '#059669' }; // Green
        case 'CANCELLED': return { backgroundColor: '#fef2f2', color: '#dc2626' }; // Red
        case 'PENDING': return { backgroundColor: '#eff6ff', color: '#2563eb' };   // Blue
        default: return { backgroundColor: '#f9fafb', color: '#6b7280' };
    }
};

const styles = {
    orderRow: {
        backgroundColor: '#fff',
        borderRadius: '16px',
        padding: '20px 24px',
        marginBottom: '12px',
        border: '1px solid #e2e8f0',
        transition: 'transform 0.2s ease',
        boxShadow: '0 1px 3px rgba(0,0,0,0.02)'
    },
    orderGrid: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        gap: '20px',
        flexWrap: 'wrap'
    },
    metaLabel: {
        display: 'block',
        fontSize: '11px',
        fontWeight: '700',
        color: '#94a3b8',
        textTransform: 'uppercase',
        letterSpacing: '0.05em',
        marginBottom: '4px'
    },
    dateText: { fontSize: '15px', fontWeight: '500', color: '#1e293b' },
    orderNumber: { fontSize: '16px', fontWeight: '700', color: '#1e293b' },
    priceText: { fontSize: '16px', fontWeight: '800', color: '#0f172a' },
    statusBadge: {
        padding: '6px 12px',
        borderRadius: '8px',
        fontSize: '12px',
        fontWeight: '700',
        display: 'inline-block'
    },
    detailsBtn: {
        backgroundColor: 'transparent',
        border: '1px solid #e2e8f0',
        padding: '8px 16px',
        borderRadius: '10px',
        fontSize: '13px',
        fontWeight: '600',
        color: '#475569',
        cursor: 'pointer',
        transition: 'all 0.2s ease'
    },
    // Flex basis for alignment
    dateInfo: { flex: '1 1 120px' },
    mainInfo: { flex: '1 1 150px' },
    amountInfo: { flex: '1 1 100px' },
    statusInfo: { flex: '1 1 100px', textAlign: 'center' },
    actionInfo: { flex: '0 0 auto' }
};