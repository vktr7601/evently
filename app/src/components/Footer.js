import React from 'react';
import { Link } from 'react-router-dom';

function Footer() {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="bg-white border-top pt-5 pb-4 mt-auto">
            <div className="container">
                <div className="row gy-4">
                    
                    {/* Column 1: Brand & Description */}
                    <div className="col-12 col-md-4">
                        <div className="d-flex align-items-center mb-3">
                            <img
                                src="https://evently-spring.s3.eu-north-1.amazonaws.com/Gemini_Generated_Image_uxcs1zuxcs1zuxcs.png"
                                alt="Logo"
                                style={{ width: '30px', height: '30px' }}
                            />
                            <span className="fw-bold fs-5 ms-2 text-dark">Evently</span>
                        </div>
                        <p className="text-muted small pr-lg-5" style={{ lineHeight: '1.6' }}>
                            The world’s leading platform for discovering and booking unique local events. 
                            Join our community to never miss a beat.
                        </p>
                    </div>

                    {/* Column 2: Quick Links */}
                    <div className="col-6 col-md-2">
                        <h6 className="fw-bold text-uppercase mb-3" style={{ fontSize: '0.8rem', letterSpacing: '1px' }}>Platform</h6>
                        <ul className="list-unstyled small">
                            <li className="mb-2"><Link to="/events" className="text-decoration-none text-muted">Browse Events</Link></li>
                            <li className="mb-2"><Link to="/performers" className="text-decoration-none text-muted">Performers</Link></li>
                            <li className="mb-2"><Link to="/venues" className="text-decoration-none text-muted">Venues</Link></li>
                        </ul>
                    </div>

                    {/* Column 3: Support */}
                    <div className="col-6 col-md-2">
                        <h6 className="fw-bold text-uppercase mb-3" style={{ fontSize: '0.8rem', letterSpacing: '1px' }}>Support</h6>
                        <ul className="list-unstyled small">
                            <li className="mb-2"><Link to="/help" className="text-decoration-none text-muted">Help Center</Link></li>
                            <li className="mb-2"><Link to="/contact" className="text-decoration-none text-muted">Contact Us</Link></li>
                        </ul>
                    </div>

                    {/* Column 4: Newsletter/Social */}
                    <div className="col-12 col-md-4">
                        <h6 className="fw-bold text-uppercase mb-3" style={{ fontSize: '0.8rem', letterSpacing: '1px' }}>Stay Connected</h6>
                        <div className="d-flex gap-3">
                            <a href="#" className="text-muted"><i className="bi bi-facebook fs-5"></i></a>
                            <a href="#" className="text-muted"><i className="bi bi-twitter-x fs-5"></i></a>
                            <a href="#" className="text-muted"><i className="bi bi-instagram fs-5"></i></a>
                            <a href="#" className="text-muted"><i className="bi bi-linkedin fs-5"></i></a>
                        </div>
                    </div>
                </div>

                <hr className="my-4 opacity-50" />

                {/* Bottom Bar */}
                <div className="row align-items-center">
                    <div className="col-md-6 text-center text-md-start">
                        <span className="text-muted small">
                            © {currentYear} <strong>Evently Inc.</strong> All rights reserved.
                        </span>
                    </div>
                    <div className="col-md-6 text-center text-md-end mt-3 mt-md-0">
                        <Link to="/privacy" className="text-decoration-none text-muted small me-3">Privacy Policy</Link>
                        <Link to="/terms" className="text-decoration-none text-muted small">Terms of Service</Link>
                    </div>
                </div>
            </div>
        </footer>
    );
}

export default Footer;