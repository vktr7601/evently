import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const OrderPayment = () => {
    const navigate = useNavigate();
    const [isProcessing, setIsProcessing] = useState(false);
    const [order, setOrder] = useState(null);
    const [timeLeft, setTimeLeft] = useState("");
    const [promoCode, setPromoCode] = useState("");

    // Payment form state
    const [cardNumber, setCardNumber] = useState("");
    const [expiry, setExpiry] = useState("");
    const [cvc, setCvc] = useState("");

    useEffect(() => {
        const headers = { "X-User-Id": 1 };
        axios.get(`http://localhost:8081/orders/active`, { headers })
            .then(res => setOrder(res.data))
            .catch(err => console.error("Error fetching order:", err));
    }, []);

    const handlePayment = () => {
        setIsProcessing(true);
        axios.post(`http://localhost:8081/orders/confirm`, {
            card_number: cardNumber,
            card_expiry : expiry,
            card_cvv: cvc,
            promo_code: promoCode || ""
        }, { headers: { "X-User-Id": 1 } })
        .then(() => {
            setIsProcessing(false);
            alert("Payment Successful!");
            navigate('/orders');
        })
        .catch(err => {
            setIsProcessing(false);
            console.error("Error processing payment:", err);
            alert("Payment Failed.");
        });
    };

    const handleCancel = () => {
        axios.delete(`http://localhost:8081/orders/cancel`, { headers: { "X-User-Id": 1 } })
            .then(() => {
                alert("Order Cancelled");
                setOrder(null);
                navigate("/events");
            })
            .catch(err => {
                console.error("Error cancelling order:", err);
                alert("Failed to cancel order.");
            });
    };

    // Helper to format dates from ISO String
    const formatDateObj = (dateString) => {
        const date = new Date(dateString);
        return isNaN(date.getTime()) ? null : date;
    };

    const expiryDate = useMemo(() => order ? formatDateObj(order.expirationTime) : null, [order]);

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
                        {order?.ticketListItems ? order.ticketListItems.map((t) => {
                            const dateObj = formatDateObj(t.eventDate);
                            return (
                                <div key={t.id} style={styles.ticketCard}>
                                    <div style={styles.ticketGrid}>
                                        <div style={styles.dateCol}>
                                            <div style={styles.dateDay}>
                                                {dateObj?.toLocaleDateString('en-US', { day: '2-digit', month: 'short' })}
                                            </div>
                                            <div style={styles.dateTime}>
                                                {dateObj?.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                            </div>
                                        </div>
                                        <div style={styles.infoCol}>
                                            <h4 style={styles.eventNameText}>{t.eventName}</h4>
                                            <p style={styles.locationLabel}>{t.eventLocationName || 'General Admission'}</p>
                                        </div>
                                        <div style={styles.priceCol}>
                                            <span style={styles.priceText}>${t.price?.toFixed(2)}</span>
                                        </div>
                                    </div>
                                </div>
                            );
                        }) : <p>Loading your tickets...</p>}
                    </div>
                </div>

                {/* RIGHT COLUMN: Payment & Promo */}
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
                            <input type="text" placeholder="0000 0000 0000 0000" style={styles.input} value={cardNumber} onChange={(e) => setCardNumber(e.target.value)} />
                        </div>

                        <div style={{ display: 'flex', gap: '15px', marginTop: '15px' }}>
                            <div style={{ flex: 1 }}>
                                <label style={styles.label}>Expiry</label>
                                <input type="text" placeholder="MM/YY" style={styles.input} value={expiry} onChange={(e) => setExpiry(e.target.value)} />
                            </div>
                            <div style={{ flex: 1 }}>
                                <label style={styles.label}>CVC</label>
                                <input type="text" placeholder="123" style={styles.input} value={cvc} onChange={(e) => setCvc(e.target.value)} />
                            </div>
                        </div>

                        {/* PROMO SECTION INTEGRATED */}
                        <div style={styles.promoSection}>
                            <label style={styles.label}>Promo Code</label>
                            <div style={styles.promoInputGroup}>
                                <input
                                    type="text"
                                    style={{ ...styles.input, marginTop: 0 }}
                                    placeholder="GIFT2026"
                                    value={promoCode}
                                    onChange={(e) => setPromoCode(e.target.value.toUpperCase())}
                                />
                                <button style={styles.applyButton}>Apply</button>
                            </div>
                        </div>

                        <div style={styles.divider}></div>

                        <div style={styles.totalRow}>
                            <span>Total</span>
                            <span style={styles.totalAmount}>${order ? order.totalPrice.toFixed(2) : '0.00'}</span>
                        </div>

                        <div style={styles.buttonGroup}>
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

                            <button
                                style={{
                                    ...styles.cancelButton,
                                    opacity: isProcessing ? 0.5 : 1,
                                    cursor: isProcessing ? 'not-allowed' : 'pointer'
                                }}
                                onClick={handleCancel}
                                disabled={isProcessing}
                            >
                                Cancel Order
                            </button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
};

const styles = {
    page: { minHeight: '100vh', backgroundColor: '#f1f5f9', display: 'flex', justifyContent: 'center', padding: '60px 20px', fontFamily: 'system-ui, sans-serif' },
    container: { display: 'flex', width: '100%', maxWidth: '1100px', gap: '40px', flexWrap: 'wrap' },
    leftCol: { flex: '1.5', minWidth: '350px' },
    rightCol: { flex: '1', minWidth: '350px', position: 'sticky', top: '40px' },
    sectionHeader: { marginBottom: '30px' },
    mainTitle: { fontSize: '32px', fontWeight: '800', color: '#0f172a', margin: '0' },
    accentBar: { height: '5px', width: '60px', backgroundColor: '#2563eb', borderRadius: '10px', marginTop: '10px' },
    ticketList: { display: 'flex', flexDirection: 'column', gap: '15px' },
    ticketCard: { backgroundColor: '#fff', borderRadius: '20px', padding: '24px', border: '1px solid #e2e8f0', boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.05)' },
    ticketGrid: { display: 'flex', alignItems: 'center', gap: '20px' },
    dateCol: { paddingRight: '20px', borderRight: '2px solid #f1f5f9', textAlign: 'center', minWidth: '85px' },
    dateDay: { fontSize: '18px', fontWeight: '800', color: '#1e293b' },
    dateTime: { fontSize: '13px', color: '#64748b' },
    infoCol: { flex: 1 },
    eventNameText: { margin: 0, fontSize: '18px', fontWeight: '700', color: '#1e293b' },
    locationLabel: { margin: '6px 0 0', fontSize: '13px', color: '#2563eb', fontWeight: '600' },
    priceText: { fontSize: '20px', fontWeight: '800', color: '#1e293b' },
    paymentCard: { backgroundColor: '#fff', borderRadius: '24px', padding: '32px', border: '1px solid #e2e8f0', boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1)' },
    paymentHeader: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '25px' },
    paymentTitle: { margin: 0, fontSize: '20px', fontWeight: '700' },
    timerBadge: { backgroundColor: '#fff7ed', color: '#ea580c', padding: '6px 12px', borderRadius: '10px', fontSize: '13px', fontWeight: '700', display: 'flex', alignItems: 'center', gap: '6px' },
    dot: { width: '8px', height: '8px', backgroundColor: '#ea580c', borderRadius: '50%' },
    label: { display: 'block', fontSize: '14px', fontWeight: '600', color: '#475569', marginBottom: '8px' },
    input: { width: '100%', padding: '14px', borderRadius: '12px', border: '1px solid #cbd5e1', fontSize: '16px', boxSizing: 'border-box' },
    promoSection: { marginTop: '20px' },
    promoInputGroup: { display: 'flex', gap: '10px' },
    applyButton: { backgroundColor: '#0f172a', color: '#fff', border: 'none', padding: '0 20px', borderRadius: '12px', fontWeight: '600', cursor: 'pointer' },
    divider: { height: '1px', backgroundColor: '#e2e8f0', margin: '25px 0' },
    summaryRow: { display: 'flex', justifyContent: 'space-between', color: '#64748b', marginBottom: '10px' },
    totalRow: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '25px' },
    totalAmount: { fontSize: '28px', fontWeight: '800', color: '#0f172a' },
    buttonGroup: { display: 'flex', flexDirection: 'column', gap: '12px' },
    payButton: { width: '100%', backgroundColor: '#2563eb', color: '#fff', border: 'none', padding: '18px', borderRadius: '16px', fontSize: '16px', fontWeight: '700' },
    cancelButton: { width: '100%', backgroundColor: '#fff', color: '#f90000', border: '1px solid #f90000', padding: '14px', borderRadius: '16px', fontSize: '15px', fontWeight: '600' }
};

export default OrderPayment;