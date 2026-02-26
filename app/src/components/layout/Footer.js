import { Link } from 'react-router-dom';
import { ROUTES } from '../../constants/routes';

function Footer() {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="bg-white border-top pt-5 pb-4 mt-auto">
            <div className="container">
                <div className="row gy-4">

                    <div className="col-12 col-md-4">
                        <div className="d-flex align-items-center mb-3">
                            <img
                                src="https://evently-spring.s3.eu-north-1.amazonaws.com/internal/evently_logo.jpg"
                                alt="Logo"
                                className="rounded-circle shadow-sm"
                                style={{ width: '32px', height: '32px', objectFit: 'cover' }}
                            />
                            <span className="fw-black fs-4 ms-2 text-dark tracking-tight">Evently</span>
                        </div>
                        <p className="text-muted small pe-lg-5" style={{ lineHeight: '1.7' }}>
                            Discover and book unique local events across Bulgaria.
                            From jazz nights to art galleries, we bring the community together.
                        </p>
                    </div>

                    <div className="col-6 col-md-2">
                        <h6 className="fw-bold text-uppercase mb-4 tracking-wider text-dark" style={{ fontSize: '0.75rem' }}>Platform</h6>
                        <ul className="list-unstyled footer-links">
                            <li className="mb-2"><Link to={ROUTES.EVENTS.BASE} className="text-muted small text-decoration-none hover-primary">Browse Events</Link></li>
                            <li className="mb-2"><Link to={ROUTES.ARTISTS.BASE} className="text-muted small text-decoration-none hover-primary">Browse Artists</Link></li>
                            <li className="mb-2"><Link to={ROUTES.LOCATIONS.BASE} className="text-muted small text-decoration-none hover-primary">Browse Locations</Link></li>
                        </ul>
                    </div>

                    <div className="col-6 col-md-2">
                        <h6 className="fw-bold text-uppercase mb-4 tracking-wider text-dark" style={{ fontSize: '0.75rem' }}>Support</h6>
                        <ul className="list-unstyled footer-links">
                            <li className="mb-2"><Link to="/help" className="text-muted small text-decoration-none hover-primary">Help Center</Link></li>
                            <li className="mb-2"><Link to="/contact" className="text-muted small text-decoration-none hover-primary">Contact Us</Link></li>
                            <li className="mb-2"><Link to={ROUTES.AUTH.LOGIN} className="text-muted small text-decoration-none hover-primary">Account</Link></li>
                        </ul>
                    </div>

                    <div className="col-12 col-md-4">
                        <h6 className="fw-bold text-uppercase mb-4 tracking-wider text-dark" style={{ fontSize: '0.75rem' }}>Stay Connected</h6>
                        <div className="d-flex gap-3 mb-4">
                            <a href="#" className="social-icon bg-light text-muted"><i className="bi bi-facebook"></i></a>
                            <a href="#" className="social-icon bg-light text-muted"><i className="bi bi-twitter-x"></i></a>
                            <a href="#" className="social-icon bg-light text-muted"><i className="bi bi-instagram"></i></a>
                            <a href="#" className="social-icon bg-light text-muted"><i className="bi bi-linkedin"></i></a>
                        </div>
                        <p className="text-muted" style={{ fontSize: '0.7rem' }}>
                            Join 10,000+ event lovers subscribing to our weekly newsletter.
                        </p>
                    </div>
                </div>

                <hr className="my-4 opacity-25" />

                <div className="row align-items-center">
                    <div className="col-md-6 text-center text-md-start">
                        <span className="text-muted" style={{ fontSize: '0.8rem' }}>
                            © {currentYear} <span className="fw-bold text-dark">Evently Inc.</span> All rights reserved.
                        </span>
                    </div>
                    <div className="col-md-6 text-center text-md-end mt-3 mt-md-0">
                        <Link to="/privacy" className="text-muted small text-decoration-none me-4 hover-dark">Privacy Policy</Link>
                        <Link to="/terms" className="text-muted small text-decoration-none hover-dark">Terms of Service</Link>
                    </div>
                </div>
            </div>
        </footer>
    );
}

export default Footer;