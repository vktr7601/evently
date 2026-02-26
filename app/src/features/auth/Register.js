import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import MessageModal from '../../components/modals/MessageModal';
import RegisterDataGenerator from '../../utils/RegisterDataGenerator';
import axiosClient from '../../api/axiosClient';
import Hero from '../../components/layout/Hero';
import { ROUTES } from '../../constants/routes';

const FormField = ({ label, children }) => (
    <div className="mb-3">
        <label className="form-label small fw-bold text-muted">{label}</label>
        {children}
    </div>
);

const TagSelector = ({ label, icon, items, selected, onToggle, getLabel, getId }) => (
    <div className="mb-4 p-4 rounded-4 border bg-white shadow-sm">
        <label className="form-label small fw-bold text-dark d-block mb-3">
            <i className={`bi ${icon} text-primary me-2`}></i>{label}
        </label>
        <div className="d-flex flex-wrap gap-2">
            {items.map((item) => {
                const isActive = selected.some(s => s.id === getId(item));
                return (
                    <button
                        key={getId(item)}
                        type="button"
                        onClick={() => onToggle(item)}
                        className={`btn btn-sm rounded-pill px-3 py-2 ${isActive ? 'btn-primary shadow-sm' : 'btn-outline-light text-dark border-secondary-subtle'
                            }`}
                    >
                        {getLabel(item)}
                        {isActive && <i className="bi bi-check-lg ms-1"></i>}
                    </button>
                );
            })}
        </div>
    </div>
);

const ADMIN_EMAIL_DOMAIN = '@admin.evently.com';

const Register = () => {
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);
    const [locations, setLocations] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [role, setRole] = useState('USER');
    const [modal, setModal] = useState({ show: false, title: '', messages: '', type: 'info' });

    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        age: '',
        email: '',
        password: '',
        confirmPassword: '',
        eventsCategories: [],
        locations: [],
        subscribeNewsletter: false
    });

    useEffect(() => {
        const fetchMasterData = async () => {
            try {
                const [locRes, catRes] = await Promise.all([
                    axiosClient.get(`${ROUTES.LOCATIONS.BASE}`),
                    axiosClient.get(`${ROUTES.CATEGORIES.BASE}`)
                ]);
                setLocations(locRes.data);
                setCategories(catRes.data);
            } catch (err) {
                console.error("Error fetching master data:", err);
            }
        };
        fetchMasterData();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleToggle = (key, item) => {
        setFormData(prev => {
            const isSelected = prev[key].some(s => s.id === item.id);
            return {
                ...prev,
                [key]: isSelected
                    ? prev[key].filter(s => s.id !== item.id)
                    : [...prev[key], item]
            };
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            setError("Passwords do not match.");
            return;
        }
        if (role === 'ADMIN' && !formData.email.includes(ADMIN_EMAIL_DOMAIN)) {
            setError(`Admin accounts require an ${ADMIN_EMAIL_DOMAIN} email address.`);
            return;
        }
        if (role === 'USER' && formData.email.includes(ADMIN_EMAIL_DOMAIN)) {
            setError("Regular accounts cannot use an admin email address.");
            return;
        }
        setLoading(true);
        setError("");
        const payload = {
            firstName: formData.firstName,
            lastName: formData.lastName,
            age: formData.age,
            email: formData.email,
            password: formData.password,
            confirmPassword: formData.confirmPassword,
            eventsCategories: formData.eventsCategories.map(c => c.id),
            locations: formData.locations.map(l => l.id),
            isSubscribedToNewsletter: formData.subscribeNewsletter
        };
        try {
            const res = await axiosClient.post(`${ROUTES.AUTH.USER_REGISTER}`, payload);
            setModal({
                show: true,
                title: 'Registration Successful',
                messages: [
                    `Welcome, ${formData.firstName}! Your account has been created.`,
                    'You will be redirected to the login page.'
                ],
                type: 'success'
            });
        } catch (err) {
            setError(err.response?.data?.message || "Registration failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const fillTestData = () => {
        const generated = role === 'ADMIN'
            ? RegisterDataGenerator.generateAdmin()
            : RegisterDataGenerator.generateUser(categories, locations);
        setFormData(prev => ({ ...prev, ...generated }));
        console.log('[Test Data] Generated credentials:', { email: generated.email, password: generated.password });
        setModal({
            show: true,
            title: 'Test Data Generated',
            messages: [
                `Email: ${generated.email}`,
                `Password: ${generated.password}`,
                'Full credentials have also been logged to the console (F12).'
            ],
            type: 'info'
        });
    };

    const passwordMismatch = formData.confirmPassword && formData.password !== formData.confirmPassword;
    const passwordMatch = formData.confirmPassword && formData.password === formData.confirmPassword;

    return (
        <div className="bg-light min-vh-100">
            <MessageModal
                show={modal.show}
                title={modal.title}
                messages={modal.messages}
                type={modal.type}
                onClose={() => {
                    if (modal.type === 'success') navigate(ROUTES.AUTH.LOGIN);
                    setModal(prev => ({ ...prev, show: false }));
                }}
            />
            <Hero
                badge="✨ Start Your Journey"
                title="Join the Evently"
                highlight="Community."
                subtitle={<>Create an account to get <span className="text-dark fw-medium">early access</span> to tickets and follow the performers you love.</>}
                primaryAction={{ text: "Already a Member?", link: "/login" }}
                secondaryAction={{ text: "Browse Events", link: "/events" }}
            />

            <div className="container" style={{ marginTop: "-50px", position: "relative", zIndex: "10" }}>
                <div className="row justify-content-center">
                    <div className="col-12 col-lg-8 col-xl-7">
                        <div className="card shadow-lg border-0 rounded-4">
                            <div className="card-body p-4 p-md-5">
                                <div className="d-flex justify-content-between align-items-center mb-4">
                                    <h4 className="fw-black text-dark mb-0">Create Account</h4>
                                    <button type="button" className="btn btn-sm btn-outline-secondary rounded-pill px-3" onClick={fillTestData}>
                                        <i className="bi bi-lightning me-1"></i>Fill Test Data
                                    </button>
                                </div>

                                {error && (
                                    <div className="alert alert-danger border-0 small py-2 mb-4 text-center">
                                        {error}
                                    </div>
                                )}

                                <form onSubmit={handleSubmit}>
                                    <div className="mb-4">
                                        <label className="form-label small fw-bold text-muted d-block">ACCOUNT TYPE</label>
                                        <div className="d-flex gap-2">
                                            <button
                                                type="button"
                                                onClick={() => setRole('USER')}
                                                className={`btn flex-fill rounded-pill py-2 fw-bold ${role === 'USER' ? 'btn-primary shadow-sm' : 'btn-outline-secondary'}`}
                                            >
                                                <i className="bi bi-person me-2"></i>Regular User
                                            </button>
                                            <button
                                                type="button"
                                                onClick={() => setRole('ADMIN')}
                                                className={`btn flex-fill rounded-pill py-2 fw-bold ${role === 'ADMIN' ? 'btn-dark shadow-sm' : 'btn-outline-secondary'}`}
                                            >
                                                <i className="bi bi-shield-lock me-2"></i>Admin
                                            </button>
                                        </div>
                                        {role === 'ADMIN' && (
                                            <div className="mt-2 small text-muted">
                                                <i className="bi bi-info-circle me-1"></i>
                                                Admin accounts require an <span className="fw-bold text-dark">{ADMIN_EMAIL_DOMAIN}</span> email address.
                                            </div>
                                        )}
                                    </div>

                                    <FormField label="FIRST NAME">
                                        <input name="firstName" type="text" className="form-control bg-light border-0 py-2" placeholder="John" value={formData.firstName} onChange={handleChange} required />
                                    </FormField>

                                    <FormField label="LAST NAME">
                                        <input name="lastName" type="text" className="form-control bg-light border-0 py-2" placeholder="Doe" value={formData.lastName} onChange={handleChange} required />
                                    </FormField>

                                    <FormField label="AGE">
                                        <input name="age" type="number" className="form-control bg-light border-0 py-2" placeholder="21" value={formData.age} onChange={handleChange} />
                                    </FormField>

                                    <FormField label="EMAIL ADDRESS">
                                        <input name="email" type="email" className="form-control bg-light border-0 py-2" placeholder={role === 'ADMIN' ? `john${ADMIN_EMAIL_DOMAIN}` : 'john@example.com'} value={formData.email} onChange={handleChange} required />
                                    </FormField>

                                    <FormField label="PASSWORD">
                                        <input name="password" type="password" className="form-control bg-light border-0 py-2" placeholder="••••••••" value={formData.password} onChange={handleChange} required />
                                    </FormField>

                                    <div className="mb-4">
                                        <label className="form-label small fw-bold text-muted">CONFIRM PASSWORD</label>
                                        <input
                                            name="confirmPassword"
                                            type="password"
                                            className={`form-control bg-light border-0 py-2 ${passwordMismatch ? 'is-invalid' : passwordMatch ? 'is-valid' : ''}`}
                                            placeholder="••••••••"
                                            value={formData.confirmPassword}
                                            onChange={handleChange}
                                            required
                                        />
                                        {passwordMismatch && <div className="invalid-feedback">Passwords do not match.</div>}
                                        {passwordMatch && <div className="valid-feedback">Passwords match.</div>}
                                    </div>

                                    {role === 'USER' && (
                                        <>
                                            <TagSelector
                                                label="Select Your Interests"
                                                icon="bi-stars"
                                                items={categories}
                                                selected={formData.eventsCategories}
                                                onToggle={(cat) => handleToggle('eventsCategories', cat)}
                                                getLabel={(cat) => cat.categoryName || cat.name}
                                                getId={(cat) => cat.id}
                                            />

                                            <TagSelector
                                                label="Select Your Locations"
                                                icon="bi-geo-alt"
                                                items={locations}
                                                selected={formData.locations}
                                                onToggle={(loc) => handleToggle('locations', loc)}
                                                getLabel={(loc) => loc.name}
                                                getId={(loc) => loc.id}
                                            />
                                        </>
                                    )}

                                    <div className="mb-4 d-flex align-items-start gap-2">
                                        <input
                                            id="subscribeNewsletter"
                                            name="subscribeNewsletter"
                                            type="checkbox"
                                            className="form-check-input mt-1 flex-shrink-0"
                                            checked={formData.subscribeNewsletter}
                                            onChange={(e) => setFormData(prev => ({ ...prev, subscribeNewsletter: e.target.checked }))}
                                        />
                                        <label htmlFor="subscribeNewsletter" className="form-check-label small text-muted" style={{ cursor: 'pointer' }}>
                                            <span className="fw-bold text-dark">Subscribe to our newsletter</span><br />
                                            Get early access to new events, exclusive offers, and updates straight to your inbox.
                                        </label>
                                    </div>

                                    <button
                                        type="submit"
                                        className={`btn w-100 rounded-pill py-3 fw-bold shadow-sm ${role === 'ADMIN' ? 'btn-dark' : 'btn-primary'}`}
                                        disabled={loading}
                                    >
                                        {loading ? "Creating Account..." : role === 'ADMIN' ? "Register as Admin" : "Register as User"}
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className="py-5"></div>
        </div>
    );
};

export default Register;
