import { useState } from "react";
import axiosClient from "../../api/axiosClient";
import { ROUTES } from "../../constants/routes";
import ErrorModal from "../../components/modals/ErrorModal";

const AdminPromoCodes = () => {
    const [form, setForm] = useState({
        promoCode: "",
        discountPercentage: "",
        discountType: "Percentage",
        expiryDate: "",
    });
    const [errorState, setErrorState] = useState({ show: false, title: '', messages: [] });
    const [success, setSuccess] = useState("");

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    // const handleError = (err) => {
    //     let title = "Submission Failed";
    //     let messages = ["An unexpected error occurred. Please try again."];

    //     if (err.response) {
    //         const status = err.response.status;
    //         if (status === 400) {
    //             title = "Validation Errors";
    //             messages = typeof err.response.data === 'object'
    //                 ? Object.values(err.response.data)
    //                 : [err.response.data];
    //         } else if (status === 409) {
    //             title = "Conflict";
    //             messages = [err.response.data];
    //         } else if (status === 500) {
    //             title = "Server Error";
    //             messages = ["Our systems are having trouble. Please contact support."];
    //         }
    //     } else if (err.request) {
    //         messages = ["Unable to reach the server. Please check your internet connection."];
    //     }
    //     setErrorState({ show: true, title, messages });
    // };

    const handleSubmit = () => {
        const payload = {
            promoCode: form.promoCode,
            discountPercentage: parseFloat(form.discountPercentage),
            discountType: form.discountType,
            expiresAt: new Date(form.expiryDate).toISOString()
        };
        setSuccess("");
        console.log("Submitting form:", payload);
        axiosClient.post(ROUTES.PROMO_CODES.ADMIN, payload)
            .then(() => {
                setSuccess("Promo code created successfully!");
            })
            .catch((err) => handleError(err));
    };

    const handleError = (err) => {
        let title = "Submission Failed";
        let messages = ["An unexpected error occurred. Please try again."];

        if (err.response) {
            const status = err.response.status;
            if (status === 400) {
                title = "Validation Errors";
                messages = typeof err.response.data === 'object' 
                    ? Object.values(err.response.data) 
                    : [err.response.data];
            } else if (status === 409) {
                title = "Schedule Conflict";
                messages = [err.response.data];
            } else if (status === 500) {
                title = "Server Error";
                messages = ["Our systems are having trouble. Please contact support."];
            }
        } else if (err.request) {
            messages = ["Unable to reach the server. Please check your internet connection."];
        }
        setErrorState({ show: true, title, messages });
    };

    return (
        <div style={styles.page}>
            <div style={styles.container}>
                <header style={styles.header}>
                    <h1 style={styles.title}>Create a Promo Code</h1>
                    <p style={styles.subtitle}>Fill in the details below to create a new promotional code.</p>
                </header>

                {success && <div className="alert alert-success text-center">{success}</div>}

                <form style={styles.formCard} onSubmit={(e) => e.preventDefault()}>
                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>1. Code Details</h3>
                        <div style={styles.grid2}>
                            <div style={styles.inputGroup}>
                                <label style={styles.label}>Promo Code</label>
                                <input
                                    type="text"
                                    name="promoCode"
                                    style={styles.input}
                                    value={form.promoCode}
                                    placeholder="e.g. SUMMER20"
                                    onChange={handleChange}
                                    minLength={3}
                                    maxLength={20}
                                    required
                                />
                            </div>
                            <div style={styles.inputGroup}>
                                <label style={styles.label}>Discount Type</label>
                                <select
                                    name="discountType"
                                    style={styles.input}
                                    value={form.discountType}
                                    onChange={handleChange}
                                >
                                    <option value="Percentage">Percentage</option>
                                    <option value="Fixed">FlatAmount</option>
                                </select>
                            </div>
                        </div>
                    </section>

                    <section style={styles.section}>
                        <h3 style={styles.sectionTitle}>2. Discount & Expiry</h3>
                        <div style={styles.grid2}>
                            <div style={styles.inputGroup}>
                                <label style={styles.label}>Discount Percentage</label>
                                <input
                                    type="number"
                                    name="discountPercentage"
                                    style={styles.input}
                                    value={form.discountPercentage}
                                    placeholder="e.g. 15"
                                    onChange={handleChange}
                                    min="0"
                                    max="100"
                                    step="0.01"
                                    required
                                />
                            </div>
                            <div style={styles.inputGroup}>
                                <label style={styles.label}>Expiry Date</label>
                                <input
                                    type="datetime-local"
                                    name="expiryDate"
                                    style={styles.input}
                                    value={form.expiryDate}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                        </div>
                    </section>

                    <button type="button" style={styles.submitBtn} onClick={handleSubmit}>
                        Create Promo Code
                    </button>
                </form>
            </div>

            <ErrorModal
                show={errorState.show}
                title={errorState.title}
                messages={errorState.messages}
                onClose={() => setErrorState({ ...errorState, show: false })}
            />
        </div>
    );
};

const styles = {
    page: { backgroundColor: '#f4f7fa', minHeight: '100vh', padding: '60px 20px', fontFamily: '"Inter", sans-serif' },
    container: { maxWidth: '900px', margin: '0 auto' },
    header: { marginBottom: '40px', textAlign: 'center' },
    title: { fontSize: '36px', fontWeight: '800', color: '#1e293b', marginBottom: '10px' },
    subtitle: { color: '#64748b', fontSize: '16px' },
    formCard: { backgroundColor: '#fff', padding: '40px', borderRadius: '24px', boxShadow: '0 20px 25px -5px rgba(0,0,0,0.05)' },
    section: { marginBottom: '40px', borderBottom: '1px solid #f1f5f9', paddingBottom: '30px' },
    sectionTitle: { fontSize: '18px', fontWeight: '700', color: '#2563eb', marginBottom: '20px', textTransform: 'uppercase', letterSpacing: '1px' },
    inputGroup: { marginBottom: '20px' },
    label: { display: 'block', fontWeight: '600', color: '#334155', marginBottom: '8px', fontSize: '14px' },
    input: { width: '100%', padding: '12px 16px', borderRadius: '12px', border: '1px solid #e2e8f0', fontSize: '15px', outline: 'none', transition: 'border 0.2s', boxSizing: 'border-box' },
    grid2: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '30px' },
    submitBtn: { width: '100%', backgroundColor: '#2563eb', color: '#fff', padding: '18px', borderRadius: '14px', fontSize: '16px', fontWeight: '700', border: 'none', cursor: 'pointer', boxShadow: '0 10px 15px -3px rgba(37, 99, 235, 0.3)', marginTop: '20px' },
};

export default AdminPromoCodes;
