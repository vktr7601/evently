import axios from 'axios';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const OrderDetails = () => {
    const { number } = useParams();
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {
                const res = await axios.get(`http://localhost:8081/orders/details/${number}`, {
                    headers: { "X-User-Id": 1 }
                });
                console.log("Fetched order details:", res.data);
                setOrder(res.data);
            } catch (err) {
                console.error("Error fetching order:", err);
            } finally {
                setLoading(false); // Stop loading regardless of success/fail
            }
        };
        fetchOrderDetails();
    }, [number]);

const downloadS3Image = async (imageUrl) => {
    // Extract 'ancient_city_of_nesebar.jpg' from the URL automatically
    // const fileName = imageUrl.split('/').pop();

    try {
        const response = await fetch('https://evently-spring.s3.eu-north-1.amazonaws.com/venues/ancient_city_of_nesebar.jpg', {
            method: 'GET',
            mode: 'cors', // Crucial for S3 downloads
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        const blob = await response.blob();
        const blobUrl = window.URL.createObjectURL(blob);
        
        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = 'hello'; // Uses the extracted name
        
        document.body.appendChild(link);
        link.click();
        
        // Cleanup
        document.body.removeChild(link);
        window.URL.revokeObjectURL(blobUrl);

    } catch (err) {
        console.error("S3 Download failed. Ensure CORS is configured on bucket 'evently-spring':", err);
        alert("Could not download image. Please check your connection or bucket permissions.");
    }
};

    // GUARD: If data isn't here yet, show this instead
    if (loading) return <div style={styles.container}>Loading order details...</div>;
    if (!order) return <div style={styles.container}>Order not found.</div>;
    const parseDate = (dateString) => {
        if (!dateString) return "Date TBD";

        const date = new Date(dateString);

        // Check if the string was a valid date
        if (isNaN(date.getTime())) return "Invalid Date";

        return date.toLocaleString('en-US', {
            weekday: 'short',
            month: 'long',
            day: 'numeric',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1 style={styles.title}>Order Details</h1>
                <div style={styles.badgeContainer}>
                    <span style={{ ...styles.badge, ...getStatusStyle(order.status) }}>
                        {order.status.replace('_', ' ')}
                    </span>
                </div>
            </div>

            <div style={styles.contentGrid}>
                <div style={styles.leftCol}>
                    <h3 style={styles.sectionTitle}>Items</h3>
                    {order.ticketListItems.map(ticket => (
                        <div key={ticket.id} style={styles.ticketCard}>
                            <div style={styles.ticketIcon}>🎟️</div>
                            <div style={styles.ticketInfo}>
                                <h4 style={styles.eventName}>{ticket.eventName}</h4>
                                <p style={styles.location}>📍 {ticket.eventLocationName}</p>
                                <p style={styles.date}>{parseDate(ticket.eventDate)}</p>
                                <p style={styles.ticketId}>Ticket #{ticket.number}</p>
                                <button onClick={() => downloadS3Image(ticket.imageUrl)}> Download ticket</button>
                            </div>
                            <div style={styles.ticketPrice}>
                                ${ticket.price.toFixed(2)}
                            </div>
                        </div>
                    ))}
                </div>

                <div style={styles.rightCol}>
                    <div style={styles.summaryCard}>
                        <h3 style={styles.sectionTitle}>Summary</h3>
                        <div style={styles.summaryRow}>
                            <span>Order Number</span>
                            <span style={styles.bold}>{order.number}</span>
                        </div>
                        <div style={styles.summaryRow}>
                            <span>Order Date</span>
                            <span style={styles.bold}>{order.createdAt}</span>
                        </div>
                        <hr style={styles.divider} />
                        <div style={styles.totalRow}>
                            <span>Total Amount</span>
                            <span style={styles.totalAmount}>${order.totalPrice.toFixed(2)}</span>
                        </div>

                    
                        {order.status === "PENDING_PAYMENT" && (
                            <button style={styles.payButton}>Complete Payment</button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

const getStatusStyle = (status) => {
    if (status === "PENDING_PAYMENT") return { backgroundColor: '#fff7ed', color: '#c2410c', border: '1px solid #ffedd5' };
    return { backgroundColor: '#f0fdf4', color: '#15803d', border: '1px solid #dcfce7' };
};

const styles = {
    container: { maxWidth: '1000px', margin: '40px auto', padding: '0 20px', fontFamily: 'system-ui, sans-serif' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px' },
    title: { fontSize: '28px', fontWeight: '800', color: '#1e293b' },
    badge: { padding: '6px 12px', borderRadius: '20px', fontSize: '12px', fontWeight: '700', textTransform: 'uppercase' },
    contentGrid: { display: 'flex', gap: '30px', flexWrap: 'wrap' },
    leftCol: { flex: '2', minWidth: '350px' },
    rightCol: { flex: '1', minWidth: '300px' },
    sectionTitle: { fontSize: '16px', fontWeight: '700', color: '#64748b', textTransform: 'uppercase', marginBottom: '15px' },
    ticketCard: {
        display: 'flex', backgroundColor: '#fff', padding: '20px', borderRadius: '16px',
        border: '1px solid #e2e8f0', marginBottom: '15px', alignItems: 'center'
    },
    ticketIcon: { fontSize: '32px', marginRight: '20px' },
    ticketInfo: { flex: 1 },
    eventName: { margin: '0 0 5px 0', fontSize: '18px', color: '#0f172a' },
    location: { margin: 0, fontSize: '14px', color: '#64748b' },
    date: { margin: '5px 0', fontSize: '14px', fontWeight: '600', color: '#2563eb' },
    ticketId: { margin: 0, fontSize: '12px', color: '#94a3b8' },
    ticketPrice: { fontSize: '18px', fontWeight: '800', color: '#1e293b' },
    summaryCard: { backgroundColor: '#f8fafc', padding: '24px', borderRadius: '16px', border: '1px solid #e2e8f0' },
    summaryRow: { display: 'flex', justifyContent: 'space-between', marginBottom: '12px', fontSize: '14px', color: '#475569' },
    divider: { border: 'none', borderTop: '1px solid #e2e8f0', margin: '20px 0' },
    totalRow: { display: 'flex', justifyContent: 'space-between', alignItems: 'center' },
    totalAmount: { fontSize: '24px', fontWeight: '800', color: '#0f172a' },
    bold: { fontWeight: '700', color: '#0f172a' },
    payButton: {
        width: '100%', marginTop: '20px', padding: '14px', backgroundColor: '#2563eb',
        color: '#fff', border: 'none', borderRadius: '12px', fontWeight: '700', cursor: 'pointer'
    }
};

export default OrderDetails;