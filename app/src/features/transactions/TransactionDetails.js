import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const TransactionDetails = () => {
    const { id } = useParams();
    const [transaction, setTransaction] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axiosClient.get(ROUTES.PAYMENTS.PAYMENT_TRANSACTIONS_DETAILS(id))
            .then(res => {
                setTransaction(res.data);
                console.log("Fetched transaction details:", res.data);
            })
            .catch(err => console.error(err))
            .finally(() => setLoading(false));
    }, [id]);

    const getStatusBadgeClass = (status) => {
        switch (status) {
            case 'COMPLETED':
            case 'SUCCEEDED':
                return 'bg-success-subtle text-success';
            case 'REFUNDED':
                return 'bg-warning-subtle text-warning-emphasis';
            case 'FAILED':
                return 'bg-danger-subtle text-danger';
            default:
                return 'bg-secondary-subtle text-secondary';
        }
    };

    if (loading) return <div className="container mt-5 text-center text-muted">Loading transaction details...</div>;
    if (!transaction) return <div className="container mt-5 text-center text-danger">Transaction not found.</div>;

    return (
        <div className="container my-5" style={{ maxWidth: '1100px' }}>

            <div className="card border-0 shadow-lg rounded-4 overflow-hidden mb-5">
                <div className="card-header bg-white border-0 p-4 pt-5">
                    <div className="d-flex justify-content-between align-items-start">
                        <div>
                            <Link to={ROUTES.ORDERS.DETAILS(transaction.orderNumber)} className="text-decoration-none small fw-bold text-uppercase text-primary mb-2 d-block">
                                ← Back to Order
                            </Link>
                            <h2 className="fw-extrabold mb-1">Transaction Details</h2>
                            <p className="text-muted mb-0">
                                {new Date(transaction.paymentTransactionDateTime).toLocaleDateString(undefined, { dateStyle: 'long' })}
                            </p>
                        </div>
                        <span className={`badge rounded-pill px-4 py-2 fs-6 ${getStatusBadgeClass(transaction.paymentTransactionStatus)}`}>
                            {transaction.paymentTransactionStatus.replace('_', ' ')}
                        </span>
                    </div>
                </div>

                <div className="card-body p-4 pb-5">
                    <div className="row g-4 text-center text-md-start">
                        <div className="col-md-3 border-end-md">
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Amount</label>
                            <h3 className="fw-bold text-success mb-0">${transaction.amount.toFixed(2)}</h3>
                        </div>
                        <div className="col-md-3 border-end-md">
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Provider</label>
                            <h5 className="fw-bold mb-0">{transaction.providerName}</h5>
                        </div>
                        <div className="col-md-3 border-end-md">
                            <label className="text-muted small fw-bold text-uppercase d-block mb-1">Provider Transaction ID</label>
                            <p className="fw-bold mb-0" style={{ fontFamily: 'monospace', fontSize: '13px' }}>
                                {transaction.transactionId}
                            </p>
                        </div>
                        <div className="col-md-3 d-flex justify-content-md-end align-items-center">
                            {transaction.receiptUrl && (
                                <a href={transaction.receiptUrl} target="_blank" rel="noreferrer"
                                    className="btn btn-outline-dark rounded-pill px-4 fw-bold">
                                    View Receipt
                                </a>
                            )}
                        </div>
                    </div>
                </div>
            </div>

            {transaction.paymentRefunds?.length > 0 && (
                <div className="mb-4">
                    <h4 className="fw-bold mb-4">Refund History</h4>
                    {transaction.paymentRefunds.map((refund, i) => (
                        <div key={i} className="card border-0 shadow-sm rounded-3 mb-3 p-3">
                            <div className="d-flex justify-content-between align-items-center">
                                <div>
                                    <span className="fw-bold text-success fs-5">
                                        ${refund.amount.toFixed(2)} Refunded
                                    </span>
                                    <div className="text-muted small">
                                        {new Date(refund.processedAt).toLocaleDateString(undefined, { dateStyle: 'long' })}
                                    </div>
                                </div>
                                {refund.receiptUrl && (
                                    <a href={refund.receiptUrl} target="_blank" rel="noreferrer"
                                        className="btn btn-outline-success btn-sm rounded-pill px-3">
                                        View Refund Receipt
                                    </a>
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default TransactionDetails;
