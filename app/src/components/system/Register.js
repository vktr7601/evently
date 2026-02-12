import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Hero from './Hero';

const Register = () => {
    const navigate = useNavigate();
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(false);
    
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        age: '',
        email: '',
        password: '',
        selectedCategories: []
    });

    useEffect(() => {
        axios.get("http://localhost:8082/categories")
            .then(res => {
                const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
                setCategories(data);
            })
            .catch(err => console.error("Categories fetch error:", err));
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleCategoryToggle = (category) => {
        setFormData(prev => {
            const isSelected = prev.selectedCategories.some(c => c.id === category.id);
            return {
                ...prev,
                selectedCategories: isSelected
                    ? prev.selectedCategories.filter(c => c.id !== category.id)
                    : [...prev.selectedCategories, category]
            };
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        const payload = {
            first_name: formData.firstName,
            last_name: formData.lastName,
            age: formData.age,
            email: formData.email,
            password: formData.password,
            events_preferences: formData.selectedCategories.map(cat => cat.id)
        };

        axios.post("http://localhost:8085/user/register", payload)
            .then(() => {
                alert("Account created successfully!");
                navigate('/login');
            })
            .catch(err => {
                console.error("Registration error:", err.response?.data || err.message);
                alert("Registration failed.");
            })
            .finally(() => setLoading(false));
    };

    return (
        <div className="bg-light min-vh-100">
            {/* 1. Consistent Hero Section */}
            <Hero 
                badge="✨ Start Your Journey"
                title="Join the Evently"
                highlight="Community."
                subtitle={<>Create an account to get <span className="text-dark fw-medium">early access</span> to tickets and follow the performers you love.</>}
                primaryAction={{ text: "Already a Member?", link: "/login" }}
                secondaryAction={{ text: "Browse Events", link: "/events" }}
            />

            {/* 2. Overlapping Registration Form */}
            <div className="container" style={{ marginTop: "-60px", position: "relative", zIndex: "10" }}>
                <div className="row justify-content-center">
                    <div className="col-12 col-lg-8 col-xl-7">
                        
                        <div className="card shadow-lg border-0 rounded-4">
                            <div className="card-body p-4 p-md-5">
                                <div className="mb-4">
                                    <h4 className="fw-black text-dark mb-1">Create Account</h4>
                                    <p className="text-muted small">Step into the world of live entertainment.</p>
                                </div>

                                <form onSubmit={handleSubmit}>
                                    {/* Name Row */}
                                    <div className="row g-3">
                                        <div className="col-md-6 mb-3">
                                            <label className="form-label small fw-bold text-muted">FIRST NAME</label>
                                            <input name="firstName" type="text" className="form-control bg-light border-0 py-2" placeholder="John" value={formData.firstName} onChange={handleChange} required />
                                        </div>
                                        <div className="col-md-6 mb-3">
                                            <label className="form-label small fw-bold text-muted">LAST NAME</label>
                                            <input name="lastName" type="text" className="form-control bg-light border-0 py-2" placeholder="Doe" value={formData.lastName} onChange={handleChange} required />
                                        </div>
                                    </div>

                                    {/* Age/Email Row */}
                                    <div className="row g-3">
                                        <div className="col-md-4 mb-3">
                                            <label className="form-label small fw-bold text-muted">AGE</label>
                                            <input name="age" type="number" className="form-control bg-light border-0 py-2" placeholder="21" value={formData.age} onChange={handleChange} />
                                        </div>
                                        <div className="col-md-8 mb-3">
                                            <label className="form-label small fw-bold text-muted">EMAIL ADDRESS</label>
                                            <input name="email" type="email" className="form-control bg-light border-0 py-2" placeholder="john@example.com" value={formData.email} onChange={handleChange} required />
                                        </div>
                                    </div>

                                    <div className="mb-4">
                                        <label className="form-label small fw-bold text-muted">PASSWORD</label>
                                        <input name="password" type="password" className="form-control bg-light border-0 py-2" placeholder="••••••••" value={formData.password} onChange={handleChange} required />
                                    </div>

                                    {/* Preferences Area - Matching the Premium Card Look */}
                                    <div className="mb-5 p-4 rounded-4 border bg-white shadow-sm">
                                        <label className="form-label small fw-bold text-dark d-block mb-3">
                                            <i className="bi bi-stars text-primary me-2"></i>Select Your Interests
                                        </label>
                                        <div className="d-flex flex-wrap gap-2">
                                            {categories.map((cat) => {
                                                const isActive = formData.selectedCategories.some(c => c.id === cat.id);
                                                return (
                                                    <button
                                                        key={cat.id}
                                                        type="button"
                                                        onClick={() => handleCategoryToggle(cat)}
                                                        className={`btn btn-sm rounded-pill px-3 py-2 transition-all ${
                                                            isActive ? 'btn-primary shadow-sm' : 'btn-outline-light text-dark border-secondary-subtle'
                                                        }`}
                                                    >
                                                        {cat.categoryName || cat.name}
                                                        {isActive && <i className="bi bi-check-lg ms-1"></i>}
                                                    </button>
                                                );
                                            })}
                                        </div>
                                    </div>

                                    <button type="submit" className="btn btn-primary w-100 rounded-pill py-3 fw-bold shadow-sm transition-hover" disabled={loading}>
                                        {loading ? <span className="spinner-border spinner-border-sm me-2"></span> : "Complete Registration"}
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