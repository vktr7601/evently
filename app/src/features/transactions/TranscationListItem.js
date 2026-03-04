import React from 'react';
import { Link } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';

const TranscationListItem = ({ transaction }) => {
    const isRefunded = transaction.paymentTransactionStatus === 'REFUNDED';

    return (
        <Link to={ROUTES.PAYMENTS.PAYMENT_TRANSACTIONS_DETAILS(transaction.id)}
            className={`list-group-item list-group-item-action border-0 border-bottom px-3 py-2 text-decoration-none ${isRefunded ? 'bg-light' : 'bg-white'}`}
        >
            <div className="d-flex align-items-center justify-content-between">
                <div className="d-flex align-items-center gap-2 overflow-hidden">
                    <span className={`badge rounded-pill ${isRefunded ? 'bg-danger-subtle text-danger' : 'bg-success-subtle text-success'}`}>
                        {transaction.paymentTransactionStatus}
                    </span>
                    <span className={`text-truncate ${isRefunded ? 'text-secondary' : 'text-dark'}`}>
                       Transaction for Order Number:  {transaction.orderNumber}
                    </span>
                </div>
                <span className={`fw-bold ms-3 flex-shrink-0 ${isRefunded ? 'text-muted text-decoration-line-through' : 'text-dark'}`}>
                    ${transaction.amount.toFixed(2)}
                </span>
            </div>
        </Link>
    );
};

export default TranscationListItem;
