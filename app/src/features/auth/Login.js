import { Link, useNavigate } from 'react-router-dom';
import { useState } from "react";
import axios from "axios";
import Hero from '../../components/layout/Hero';
import { ROUTES } from '../../constants/routes';
import axiosClient from '../../api/axiosClient';


const Login = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        try {
            const response = await axiosClient.post("/auth/login", formData);
            const { jwtToken, userRole } = response.data;
            if (jwtToken) {
                localStorage.setItem("jwtToken", jwtToken);
                localStorage.setItem("userRole", userRole);
                window.location.href = "/";
            }
        } catch (err) {
            setError(err.response?.data?.message || "Invalid credentials.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="bg-light min-vh-100">
            <Hero
                badge="🔐 Secure Access"
                title="Your Gateway to"
                highlight="Live Music."
                subtitle={<>Welcome back to <span className="text-dark fw-medium">Evently</span>. Log in to manage your bookings and explore personalized recommendations.</>}
                primaryAction={{ text: "Create Account", link: ROUTES.AUTH.REGISTER }}
                secondaryAction={{ text: "Help Center", link: "/contact" }}
            />

            <div className="container" style={{ marginTop: "-50px", position: "relative", zIndex: "10" }}>
                <div className="row justify-content-center">
                    <div className="col-12 col-md-6 col-lg-4">

                        <div className="card shadow-lg border-0 rounded-4">
                            <div className="card-body p-4 p-md-5">
                                <h4 className="fw-black text-dark mb-4">Sign In</h4>

                                {error && (
                                    <div className="alert alert-danger border-0 small py-2 mb-4 text-center">
                                        {error}
                                    </div>
                                )}

                                <form onSubmit={handleSubmit}>
                                    <div className="mb-3">
                                        <label className="form-label small fw-bold text-muted">EMAIL</label>
                                        <input
                                            name="email"
                                            type="email"
                                            className="form-control bg-light border-0 py-2"
                                            placeholder="alex@example.com"
                                            value={formData.email}
                                            onChange={handleChange}
                                            required
                                        />
                                    </div>

                                    <div className="mb-4">
                                        <label className="form-label small fw-bold text-muted">PASSWORD</label>
                                        <input
                                            name="password"
                                            type="password"
                                            className="form-control bg-light border-0 py-2"
                                            placeholder="••••••••"
                                            value={formData.password}
                                            onChange={handleChange}
                                            required
                                        />
                                    </div>

                                    <button
                                        type="submit"
                                        className="btn btn-primary w-100 rounded-pill py-3 fw-bold shadow-sm transition-hover"
                                        disabled={loading}
                                    >
                                        {loading ? "Authenticating..." : "Login to Account"}
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;