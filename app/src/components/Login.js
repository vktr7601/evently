import { Link, useNavigate } from 'react-router-dom'; // Added useNavigate
import { useState } from "react";
import axios from "axios"; // Ensure axios is installed: npm install axios

const Login = () => {
    const navigate = useNavigate(); // For redirection
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });
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
            // Adjust this URL to your Gateway or Auth Service port
            const response = await axios.post("http://localhost:8085/user/login", formData);

            // Assuming your backend returns: { token: "eyJ...", userId: 1 }
            const { token } = response.data;

            if (token) {
                // 1. Save the token
                localStorage.setItem("userToken", token);
                

                navigate("/");
            }
        } catch (err) {
            console.error("Login error:", err);
            setError(err.response?.data?.message || "Invalid email or password. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-12 col-md-8 col-lg-6">
                    <div className="card shadow-sm border-0 p-4">
                        <div className="card-body">
                            <h2 className="card-title fw-bold mb-2 text-center">Login</h2>
                            <p className="text-muted text-center mb-4 small">Welcome back to the community</p>

                            {/* Error Alert */}
                            {error && <div className="alert alert-danger small py-2 text-center">{error}</div>}

                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label className="form-label small fw-bold text-uppercase"
                                        style={{ fontSize: '0.75rem', letterSpacing: '0.5px' }}>Email Address</label>
                                    <input
                                        name="email"
                                        type="email"
                                        className="form-control bg-light border-0"
                                        placeholder="name@example.com"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                        disabled={loading}
                                    />
                                </div>

                                <div className="mb-4">
                                    <label className="form-label small fw-bold text-uppercase"
                                        style={{ fontSize: '0.75rem', letterSpacing: '0.5px' }}>Password</label>
                                    <input
                                        name="password"
                                        type="password"
                                        className="form-control bg-light border-0"
                                        placeholder="••••••••"
                                        value={formData.password}
                                        onChange={handleChange}
                                        required
                                        disabled={loading}
                                    />
                                </div>

                                <div className="d-grid pt-2">
                                    <button
                                        type="submit"
                                        className="btn btn-dark rounded-pill py-2 fw-bold shadow-sm"
                                        disabled={loading}
                                    >
                                        {loading ? (
                                            <span className="spinner-border spinner-border-sm me-2"></span>
                                        ) : "Login Now"}
                                    </button>
                                </div>
                            </form>

                            <div className="text-center mt-4">
                                <span className="text-muted small">Don't have an account yet? </span>
                                <Link to="/register" className="text-dark small fw-bold text-decoration-none">Register now</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;