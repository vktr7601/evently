import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import ErrorModal from '../../components/modals/ErrorModal';
import { CardElement, useStripe, useElements } from '@stripe/react-stripe-js'; // Added these

const CARD_ELEMENT_OPTIONS = {
    hidePostalCode: true,
    style: {
        base: {
            fontSize: '18px',
            color: '#1e293b',
            fontFamily: 'system-ui, sans-serif',
            '::placeholder': { color: '#94a3b8' },
        },
        invalid: { color: '#ef4444', iconColor: '#ef4444' },
    },
};

const OrderPayment = () => {
    const navigate = useNavigate();
    const stripe = useStripe();
    const elements = useElements();

    // State
    const [order, setOrder] = useState(null);
    const [isProcessing, setIsProcessing] = useState(false);
    const [promoCode, setPromoCode] = useState("");
    const [timeLeft, setTimeLeft] = useState("");
    
    // UI Feedback State
    const [modal, setModal] = useState({ open: false, title: "", message: "" });

    const userId = 1; // In production, get this from Auth context
    const headers = useMemo(() => ({ "X-User-Id": userId }), [userId]);

    // 1. Fetch Active Order
    useEffect(() => {
        axios.get(`http://localhost:8081/orders/active`, { headers })
            .then(res => setOrder(res.data))
            .catch(err => {
                if (err.response?.status === 404 || err.response?.status === 410) {
                    navigate('/events');
                }
            });
    }, [navigate, headers]);

    // 2. Timer Logic
    const expiryDate = useMemo(() => 
        order?.expirationTime ? new Date(order.expirationTime) : null, 
    [order]);

    useEffect(() => {
        if (!expiryDate) return;
        const interval = setInterval(() => {
            const diff = expiryDate - new Date();
            if (diff <= 0) {
                setTimeLeft("Expired");
                clearInterval(interval);
                navigate('/events');
            } else {
                const mins = Math.floor((diff / 1000 / 60) % 60);
                const secs = Math.floor((diff / 1000) % 60);
                setTimeLeft(`${mins}m ${secs}s`);
            }
        }, 1000);
        return () => clearInterval(interval);
    }, [expiryDate, navigate]);

    // 3. Handlers
    const handlePayment = async () => {
        if (!stripe || !elements) return;

        setIsProcessing(true);
        const cardElement = elements.getElement(CardElement);

        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: 'card',
            card: cardElement,
        });

        if (error) {
            setModal({ open: true, title: "Card Error", message: error.message });
            setIsProcessing(false);
            return;
        }

        try {
            await axios.post(`http://localhost:8081/orders/confirm`, {
                stripePaymentMethodId: paymentMethod.id,
                promoCode: promoCode
            }, { headers });
            
            navigate('/orders/success');
        } catch (err) {
            const errorMsg = err.response?.data?.message || "Payment failed. Please try again.";
            setModal({ open: true, title: "Transaction Failed", message: errorMsg });
            setIsProcessing(false);
        }
    };

    const handleCancel = async () => {
        if (!window.confirm("Are you sure? Your tickets will be released.")) return;
        
        try {
            await axios.delete(`http://localhost:8081/orders/active`, { headers });
            navigate("/events");
        } catch (err) {
            navigate("/events");
        }
    };

    if (!order) return <div style={styles.loading}>Loading your secure checkout...</div>;

    return (
        <div style={styles.page}>
            <div style={styles.container}>
                {/* LEFT: Review */}
                <div style={styles.leftCol}>
                    <div style={styles.sectionHeader}>
                        <h1 style={styles.mainTitle}>Review Your Order</h1>
                        <div style={styles.accentBar}></div>
                    </div>

                    <div style={styles.ticketList}>
                        {order.tickets.map((t) => (
                            <div key={t.id} style={styles.ticketCard}>
                                <div style={styles.ticketGrid}>
                                    <div style={styles.dateCol}>
                                        <div style={styles.dateDay}>
                                            {new Date(t.eventStartTime).toLocaleDateString('en-US', { day: '2-digit', month: 'short' })}
                                        </div>
                                        <div style={styles.dateTime}>
                                            {new Date(t.eventStartTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
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
                        ))}
                    </div>
                </div>

                {/* RIGHT: Payment */}
                <div style={styles.rightCol}>
                    <div style={styles.paymentCard}>
                        <div style={styles.paymentHeader}>
                            <h2 style={styles.paymentTitle}>Payment Details</h2>
                            <div style={styles.timerBadge}>
                                <span style={styles.dot}></span>
                                {timeLeft}
                            </div>
                        </div>

                        <div style={styles.stripeInputWrapper}>
                            <label style={styles.label}>Credit or Debit Card</label>
                            <div style={styles.stripeElementContainer}>
                                <CardElement options={CARD_ELEMENT_OPTIONS} />
                            </div>
                            <small style={styles.helperText}>Secured by Stripe. We do not store your card details.</small>
                        </div>

                        <div style={styles.promoSection}>
                            <label style={styles.label}>Promo Code</label>
                            <div style={styles.promoInputGroup}>
                                <input
                                    type="text"
                                    style={styles.input}
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
                            <span style={styles.totalAmount}>${order.totalPrice.toFixed(2)}</span>
                        </div>

                        <div style={styles.buttonGroup}>
                            <button
                                style={{...styles.payButton, opacity: isProcessing ? 0.7 : 1}}
                                onClick={handlePayment}
                                disabled={isProcessing || !stripe}
                            >
                                {isProcessing ? "Processing..." : "Confirm & Pay"}
                            </button>
                            
                            <button
                                style={styles.cancelButton}
                                onClick={handleCancel}
                                disabled={isProcessing}
                            >
                                Release Tickets & Exit
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <ErrorModal
                show={modal.open}
                onClose={() => setModal({ ...modal, open: false })}
                title={modal.title}
                messages={modal.message}
            />
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
    cancelButton: { width: '100%', backgroundColor: '#fff', color: '#f90000', border: '1px solid #f90000', padding: '14px', borderRadius: '16px', fontSize: '15px', fontWeight: '600' },
    stripeInputWrapper: {
        marginTop: '25px',
        marginBottom: '25px',
    },
    label: {
        display: 'block',
        fontSize: '14px',
        fontWeight: '600',
        color: '#475569',
        marginBottom: '10px', // More space between label and input
    },
    stripeElementContainer: {
        padding: '18px 14px', // Significant padding for a "spacious" feel
        border: '1px solid #cbd5e1',
        borderRadius: '14px',
        backgroundColor: '#ffffff',
        boxShadow: 'inset 0 2px 4px 0 rgba(0, 0, 0, 0.02)', // Subtle depth
        transition: 'border-color 0.2s ease',
    },
    helperText: {
        display: 'block',
        marginTop: '8px',
        fontSize: '12px',
        color: '#94a3b8',
    },
    cancelButton: { 
    width: '100%', 
    backgroundColor: 'transparent', 
    color: '#64748b', // Subtle gray
    border: 'none', 
    padding: '10px', 
    fontSize: '14px', 
    fontWeight: '600',
    textDecoration: 'underline',
    cursor: 'pointer'
}
};


export default OrderPayment;