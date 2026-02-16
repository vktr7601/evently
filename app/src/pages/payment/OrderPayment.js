import React, { useState, useEffect, useMemo } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const OrderPayment = () => {
    const { id } = useParams();
    const [isProcessing, setIsProcessing] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [order, setOrder] = useState(null);
    const handlePayment = () => {
        setIsProcessing(true);
        // Mock API call
        setTimeout(() => {
            setIsProcessing(false);
            alert("Payment Successful!");
            setIsModalOpen(false);
        }, 2000);
    };


    const handleModalOpen = () => {
        setIsModalOpen(true);
        console.log("Opening payment modal for order ID:", id);
    }
    // Your Response JSON
    useEffect(() => {
        axios.get(`http://localhost:8081/order/payment/${id}`)
            .then(res => {

                console.log("Fetched order details:", res.data);
                setOrder(res.data);
            })
            .catch(err => {
                console.error("Error fetching order:", err);
            });
    }, [id]);

    // Helper: Convert array [Y, M, D, H, m, s] to JS Date object
    const parseDateArray = (arr) => {
        // arr[1] - 1 because JS months are 0-indexed
        return new Date(arr[0], arr[1] - 1, arr[2], arr[3] || 0, arr[4] || 0, arr[5] || 0);
    };

    const expiryDate = useMemo(() => order ? parseDateArray(order.expirationTime) : null, [order]);
    const [timeLeft, setTimeLeft] = useState("");

    // Countdown Logic
    useEffect(() => {
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

        calculateTime(); // Initial run
        const timer = setInterval(calculateTime, 1000);
        return () => clearInterval(timer);
    }, [expiryDate]);

    return (
        <div style={styles.page}>
            <div style={styles.card}>

                {/* Header */}
                <div style={styles.header}>
                    <div>
                        <h1 style={styles.title}>Complete Payment</h1>
                        <p style={styles.orderNum}>Order #{order ? order.number : ''}</p>
                    </div>
                    <div style={styles.timerBadge}>
                        <span style={styles.dot}></span>
                        {timeLeft}
                    </div>
                </div>

                {/* Ticket Details */}
                <div style={styles.content}>
                    <h2 style={styles.sectionTitle}>Your Tickets</h2>
                    {order ? order.ticket.map((t) => {
                        const date = parseDateArray(t.eventDate);
                        return (
                            <div key={t.id} style={styles.ticketItem}>
                                <div style={styles.ticketInfo}>
                                    <div style={styles.ticketIcon}>🎫</div>
                                    <div>
                                        <p style={styles.eventName}>{t.eventName}</p>
                                        <p style={styles.eventDetails}>
                                            {date.toLocaleDateString()} • {date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                        </p>
                                    </div>
                                </div>
                                <div style={styles.ticketPrice}>${t.ticketPrice.toFixed(2)}</div>
                            </div>
                        );
                    }) : null}
                </div>

                {/* Summary & Button */}
                <div style={styles.footer}>
                    <div style={styles.totalRow}>
                        <span style={styles.totalLabel}>Total Amount</span>
                        <span style={styles.totalAmount}>${order ? order.totalPrice.toFixed(2) : ''}</span>
                    </div>
                    <button
                        style={styles.payButton}
                        onClick={() => handleModalOpen()}
                        //disabled={timeLeft === "Expired"}
                    >
                        Pay Now
                    </button>
                    <p style={styles.secureText}>🔒 Secure checkout by Evently</p>
                </div>
            </div>
            {/* Payment Modal Overlay */}
                {isModalOpen && (
                    <div style={styles.overlay}>
                        <div style={styles.modal}>
                            <div style={styles.modalHeader}>
                                <h2 style={{ margin: 0 }}>Payment Details</h2>
                                <button onClick={() => setIsModalOpen(false)} style={styles.closeBtn}>✕</button>
                            </div>

                            <div style={styles.modalBody}>
                                <label style={styles.label}>Card Number</label>
                                <input type="text" placeholder="0000 0000 0000 0000" style={styles.input} />

                                <div style={{ display: 'flex', gap: '10px', marginTop: '15px' }}>
                                    <div style={{ flex: 1 }}>
                                        <label style={styles.label}>Expiry</label>
                                        <input type="text" placeholder="MM/YY" style={styles.input} />
                                    </div>
                                    <div style={{ flex: 1 }}>
                                        <label style={styles.label}>CVC</label>
                                        <input type="text" placeholder="123" style={styles.input} />
                                    </div>
                                </div>

                                <button
                                    style={{ ...styles.payButton, marginTop: '25px', backgroundColor: isProcessing ? '#94a3b8' : '#2563eb' }}
                                    onClick={handlePayment}
                                    disabled={isProcessing}
                                >
                                    {isProcessing ? "Processing..." : "Confirm Payment"}
                                </button>
                            </div>
                        </div>
                    </div>
                )}
        </div>
        
    );
};

// Vanilla CSS-in-JS Object
const styles = {
    overlay: {
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.6)', // Dims the background
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1000 // Ensures it sits on top of everything
    },
    modal: {
        backgroundColor: '#fff',
        padding: '30px',
        borderRadius: '16px',
        width: '90%',
        maxWidth: '400px',
        boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.2)',
        zIndex: 1001,
        position: 'relative' // Keeps internal elements positioned correctly
    },
    modalHeader: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: '20px'
    },
    closeBtn: {
        background: 'none',
        border: 'none',
        fontSize: '20px',
        cursor: 'pointer',
        color: '#666'
    },
    input: {
        width: '100%',
        padding: '12px',
        marginTop: '8px',
        borderRadius: '8px',
        border: '1px solid #ddd',
        fontSize: '16px',
        boxSizing: 'border-box' // Essential for width: 100%
    },
    label: {
        fontSize: '14px',
        fontWeight: '600',
        color: '#374151'
    },
    page: {
        minHeight: '100vh',
        backgroundColor: '#f3f4f6',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        fontFamily: 'system-ui, sans-serif',
        padding: '20px'
    },
    card: {
        backgroundColor: '#fff',
        width: '100%',
        maxWidth: '450px',
        borderRadius: '16px',
        boxShadow: '0 10px 25px rgba(0,0,0,0.1)',
        overflow: 'hidden'
    },
    header: {
        padding: '24px',
        borderBottom: '1px solid #eee',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
    },
    title: { margin: 0, fontSize: '20px', fontWeight: 'bold' },
    orderNum: { margin: '4px 0 0', color: '#666', fontSize: '14px' },
    timerBadge: {
        backgroundColor: '#fff7ed',
        color: '#ea580c',
        padding: '6px 12px',
        borderRadius: '20px',
        fontSize: '14px',
        fontWeight: '600',
        display: 'flex',
        alignItems: 'center',
        gap: '6px'
    },
    dot: { width: '8px', height: '8px', backgroundColor: '#ea580c', borderRadius: '50%' },
    content: { padding: '24px' },
    sectionTitle: { fontSize: '12px', color: '#999', textTransform: 'uppercase', marginBottom: '16px' },
    ticketItem: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: '#f9fafb',
        padding: '12px',
        borderRadius: '12px',
        marginBottom: '10px'
    },
    ticketInfo: { display: 'flex', alignItems: 'center', gap: '12px' },
    ticketIcon: { fontSize: '24px' },
    eventName: { margin: 0, fontWeight: '600', fontSize: '15px' },
    eventDetails: { margin: 0, fontSize: '13px', color: '#666' },
    ticketPrice: { fontWeight: 'bold' },
    footer: { padding: '24px', borderTop: '1px solid #eee' },
    totalRow: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' },
    totalLabel: { color: '#666', fontSize: '16px' },
    totalAmount: { fontSize: '28px', fontWeight: '800' },
    payButton: {
        width: '100%',
        backgroundColor: '#2563eb',
        color: '#fff',
        border: 'none',
        padding: '16px',
        borderRadius: '12px',
        fontSize: '16px',
        fontWeight: 'bold',
        cursor: 'pointer',
        transition: 'background 0.2s'
    },
    secureText: { textAlign: 'center', fontSize: '12px', color: '#aaa', marginTop: '12px' }
};



export default OrderPayment;