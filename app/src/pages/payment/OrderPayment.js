import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const OrderPayment = () => {
    const navigate = useNavigate();
    const [isProcessing, setIsProcessing] = useState(false);
    const [order, setOrder] = useState(null);
    const [timeLeft, setTimeLeft] = useState("");

    // Fetch Order Data
    useEffect(() => {
        const headers = { "X-User-Id": 1 };
        axios.get(`http://localhost:8081/orders/active`, { headers })
            .then(res => {
                setOrder(res.data);
            })
            .catch(err => {
                console.error("Error fetching order:", err);
            });
    }, []);

    const handlePayment = () => {
        setIsProcessing(true);
        setTimeout(() => {
            setIsProcessing(false);
            alert("Payment Successful!");
        }, 2000);
    };

    const handleCancel = () => {
        axios.delete(`http://localhost:8081/orders/cancel`, {
            headers: { "X-User-Id": 1 }
        })
        .then(res => {
            alert("Order Cancelled");
            setOrder(null);
            navigate("/events");
        })
        .catch(err => {
            console.error("Error cancelling order:", err);
            alert("Failed to cancel order. Please try again.");
        });
    };

    const parseDateArray = (arr) => {
        if (!arr) return new Date();
        return new Date(arr[0], arr[1] - 1, arr[2], arr[3] || 0, arr[4] || 0, arr[5] || 0);
    };

    const expiryDate = useMemo(() => order ? parseDateArray(order.expirationTime) : null, [order]);

    useEffect(() => {
        if (!expiryDate) return;
        const calculateTime = () => {
            const now = new Date();
            const diff = expiryDate - now;
            if (diff <= 0) {
                setTimeLeft("Expired");
                return;
            }
            const mins = Math.floor((diff / 1000 / 60) % 60);
            const secs = Math.floor((diff / 1000) % 60);
            setTimeLeft(`${mins}m ${secs}s`);
        };
        calculateTime();
        const timer = setInterval(calculateTime, 1000);
        return () => clearInterval(timer);
    }, [expiryDate]);

    return (
        <div style={styles.page}>
            <div style={styles.container}>

                {/* LEFT COLUMN: Order Review */}
                <div style={styles.leftCol}>
                    <div style={styles.sectionHeader}>
                        <h1 style={styles.mainTitle}>Review Your Order</h1>
                        <div style={styles.accentBar}></div>
                    </div>

                    <div style={styles.ticketList}>
                        {order && order.ticket ? order.ticket.map((t) => {
                            const date = parseDateArray(t.eventDate);
                            return (
                                <div key={t.id} style={styles.ticketCard}>
                                    <div style={styles.ticketGrid}>
                                        <div style={styles.dateCol}>
                                            <div style={styles.dateDay}>
                                                {date.toLocaleDateString('en-US', { day: '2-digit', month: 'short' })}
                                            </div>
                                            <div style={styles.dateTime}>
                                                {date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                            </div>
                                        </div>
                                        <div style={styles.infoCol}>
                                            <h4 style={styles.eventNameText}>{t.eventName}</h4>
                                            <p style={styles.locationLabel}>📍 General Admission</p>
                                        </div>
                                        <div style={styles.priceCol}>
                                            <span style={styles.priceText}>${t.ticketPrice.toFixed(2)}</span>
                                        </div>
                                    </div>
                                </div>
                            );
                        }) : <p>Loading your tickets...</p>}
                    </div>
                </div>

                {/* RIGHT COLUMN: Payment Form */}
                <div style={styles.rightCol}>
                    <div style={styles.paymentCard}>
                        <div style={styles.paymentHeader}>
                            <h2 style={styles.paymentTitle}>Payment Details</h2>
                            <div style={styles.timerBadge}>
                                <span style={styles.dot}></span>
                                {timeLeft}
                            </div>
                        </div>

                        <div style={styles.formGroup}>
                            <label style={styles.label}>Card Number</label>
                            <input type="text" placeholder="0000 0000 0000 0000" style={styles.input} />
                        </div>

                        <div style={{ display: 'flex', gap: '15px', marginTop: '15px' }}>
                            <div style={{ flex: 1 }}>
                                <label style={styles.label}>Expiry</label>
                                <input type="text" placeholder="MM/YY" style={styles.input} />
                            </div>
                            <div style={{ flex: 1 }}>
                                <label style={styles.label}>CVC</label>
                                <input type="text" placeholder="123" style={styles.input} />
                            </div>
                        </div>

                        <div style={styles.divider}></div>

                        <div style={styles.summaryRow}>
                            <span>Subtotal</span>
                            <span>${order ? order.totalPrice.toFixed(2) : '0.00'}</span>
                        </div>
                        <div style={styles.totalRow}>
                            <span>Total</span>
                            <span style={styles.totalAmount}>${order ? order.totalPrice.toFixed(2) : '0.00'}</span>
                        </div>

                        <button
                            style={{
                                ...styles.payButton,
                                opacity: timeLeft === "Expired" || isProcessing ? 0.6 : 1,
                                cursor: (timeLeft === "Expired" || isProcessing) ? 'not-allowed' : 'pointer'
                            }}
                            onClick={handlePayment}
                            disabled={timeLeft === "Expired" || isProcessing}
                        >
                            {isProcessing ? "Processing..." : timeLeft === "Expired" ? "Expired" : "Confirm & Pay"}
                        </button>

                        {/* SECONDARY BUTTON: Cancel Order */}
                        <button
                            style={{
                                ...styles.cancelButton,
                                opacity: isProcessing ? 0.5 : 1,
                                cursor: isProcessing ? 'not-allowed' : 'pointer'
                            }}
                             onClick={() =>  handleCancel()}
                            disabled={isProcessing}
                        >
                            Cancel Order
                        </button>
                    </div>
                </div>

            </div>
        </div>
    );
};

const styles = {
    page: {
        minHeight: '100vh',
        backgroundColor: '#f1f5f9',
        display: 'flex',
        justifyContent: 'center',
        padding: '60px 20px',
        fontFamily: 'system-ui, sans-serif'
    },
    payButton: {
        width: '100%',
        backgroundColor: '#2563eb', // Vibrant Blue
        color: '#fff',
        border: 'none',
        padding: '16px',
        borderRadius: '14px',
        fontSize: '16px',
        fontWeight: '700',
        transition: 'all 0.2s ease',
        boxShadow: '0 4px 12px rgba(37, 99, 235, 0.2)',
    },

    cancelButton: {
        width: '100%',
        backgroundColor: '#f90000',
        color: '#fff', // Muted Slate color
        border: '1px solid #e2e8f0', // Subtle border
        padding: '14px',
        borderRadius: '14px',
        fontSize: '15px',
        fontWeight: '600',
        transition: 'all 0.2s ease',
    },

    // Optional: Add a style for the container to keep them tidy
    buttonGroup: {
        marginTop: '20px',
        display: 'flex',
        flexDirection: 'column',
        gap: '12px'
    },
    container: {
        display: 'flex',
        width: '100%',
        maxWidth: '1100px',
        gap: '40px',
        alignItems: 'flex-start',
        // Responsive stack for mobile
        flexWrap: 'wrap'
    },
    leftCol: {
        flex: '1.5',
        minWidth: '350px'
    },
    rightCol: {
        flex: '1',
        minWidth: '350px',
        position: 'sticky',
        top: '40px'
    },
    sectionHeader: { marginBottom: '30px' },
    mainTitle: { fontSize: '32px', fontWeight: '800', color: '#0f172a', margin: '0 0 10px 0' },
    accentBar: { height: '5px', width: '60px', backgroundColor: '#2563eb', borderRadius: '10px' },

    // Ticket Card Styles
    ticketCard: {
        backgroundColor: '#fff',
        borderRadius: '20px',
        padding: '24px',
        marginBottom: '20px',
        boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.05)',
        border: '1px solid #e2e8f0'
    },
    ticketGrid: { display: 'flex', alignItems: 'center', gap: '20px' },
    dateCol: { paddingRight: '20px', borderRight: '2px solid #f1f5f9', textAlign: 'center', minWidth: '85px' },
    dateDay: { fontSize: '18px', fontWeight: '800', color: '#1e293b' },
    dateTime: { fontSize: '13px', color: '#64748b' },
    infoCol: { flex: 1 },
    eventNameText: { margin: 0, fontSize: '18px', fontWeight: '700', color: '#1e293b' },
    locationLabel: { margin: '6px 0 0', fontSize: '13px', color: '#2563eb', fontWeight: '600' },
    priceText: { fontSize: '20px', fontWeight: '800', color: '#1e293b' },

    // Payment Card Styles
    paymentCard: {
        backgroundColor: '#fff',
        borderRadius: '24px',
        padding: '32px',
        boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1)',
        border: '1px solid #e2e8f0'
    },
    paymentHeader: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '25px' },
    paymentTitle: { margin: 0, fontSize: '20px', fontWeight: '700' },
    timerBadge: {
        backgroundColor: '#fff7ed',
        color: '#ea580c',
        padding: '6px 12px',
        borderRadius: '10px',
        fontSize: '13px',
        fontWeight: '700',
        display: 'flex',
        alignItems: 'center',
        gap: '6px'
    },
    dot: { width: '8px', height: '8px', backgroundColor: '#ea580c', borderRadius: '50%' },
    label: { display: 'block', fontSize: '14px', fontWeight: '600', color: '#475569', marginBottom: '8px' },
    input: {
        width: '100%',
        padding: '14px',
        borderRadius: '12px',
        border: '1px solid #cbd5e1',
        fontSize: '16px',
        boxSizing: 'border-box'
    },
    divider: { height: '1px', backgroundColor: '#e2e8f0', margin: '25px 0' },
    summaryRow: { display: 'flex', justifyContent: 'space-between', color: '#64748b', marginBottom: '10px' },
    totalRow: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '25px', fontWeight: '800' },
    totalAmount: { fontSize: '28px', color: '#0f172a' },
    payButton: {
        width: '100%',
        backgroundColor: '#2563eb',
        color: '#fff',
        border: 'none',
        padding: '18px',
        borderRadius: '16px',
        fontSize: '16px',
        fontWeight: '700',
        boxShadow: '0 10px 15px -3px rgba(37, 99, 235, 0.4)'
    },
    secureText: { textAlign: 'center', fontSize: '12px', color: '#94a3b8', marginTop: '15px' }
};

export default OrderPayment;