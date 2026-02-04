import React from 'react';
import {Link} from 'react-router-dom';

function Footer() {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="bg-white border-top py-4 mt-auto">
            <div className="container">
                <span className="fw-bold text-primary me-2">Evently</span>
                <span className="text-muted small">
                            © {currentYear} All rights reserved.
                        </span>

                <ul className="list-inline mb-0">
                    <li className="list-inline-item">
                        <Link to="/privacy" className="text-decoration-none text-muted small px-2">Privacy
                            Policy</Link>
                    </li>
                    <li className="list-inline-item">
                        <Link to="/terms" className="text-decoration-none text-muted small px-2">Terms of
                            Service</Link>
                    </li>
                </ul>
            </div>
        </footer>
    );
}

export default Footer;