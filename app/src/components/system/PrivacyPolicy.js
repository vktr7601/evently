import Hero from '../layout/Hero';

const PrivacyPolicy = () => {
    return (
        <div className="bg-light min-vh-100">
            <Hero
                badge="⚖️ Legal Terms"
                title="Privacy"
                highlight="Policy"
                subtitle="We value your trust. Learn more about how we protect your data and ensure a secure experience at Evently."
                primaryAction={{ text: "Create Account", link: "/register" }}
                secondaryAction={{ text: "Help Center", link: "/help" }}
            />

            <div className="container" style={{ marginTop: "-60px", position: "relative", zIndex: "10" }}>
                <div className="row justify-content-center">
                    <div className="col-lg-10 col-xl-8">
                        <div className="card shadow-lg border-0 rounded-4 overflow-hidden">
                            <div className="card-body p-4 p-md-5">

                                <header className="mb-5 d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3">
                                    <div>
                                        <span className="badge bg-primary-subtle text-primary rounded-pill px-3 py-2 mb-2 fw-bold" style={{ fontSize: '0.7rem' }}>
                                            VERSION 1.2
                                        </span>
                                        <h2 className="fw-black text-dark mb-0">Commitment to Security</h2>
                                    </div>
                                    <div className="text-md-end">
                                        <span className="text-muted small d-block">
                                            <i className="bi bi-calendar3 me-2"></i>
                                            Last Updated
                                        </span>
                                        <span className="fw-bold text-dark small">February 4, 2026</span>
                                    </div>
                                </header>

                                <hr className="my-5 opacity-10" />

                                {/* Section 1 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>1</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">Information We Collect</h3>
                                    </div>
                                    <p className="text-secondary ps-5" style={{ lineHeight: '1.8' }}>
                                        At <strong className="text-dark">Evently</strong>, we collect information to provide better services to our users.
                                        This includes data you provide (name, email) and behavioral data (event preferences, search history) to help us curate your feed.
                                    </p>
                                </section>

                                {/* Section 2 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>2</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">How We Use Information</h3>
                                    </div>
                                    <div className="ps-5">
                                        <p className="text-secondary mb-4">We use your information to facilitate the best experience possible:</p>
                                        <div className="row g-3">
                                            {[
                                                "Improve platform performance",
                                                "Secure ticket booking & payments",
                                                "Personalized event alerts"
                                            ].map((text, i) => (
                                                <div key={i} className="col-md-6">
                                                    <div className="d-flex align-items-center p-3 bg-light rounded-3 border-start border-primary border-4">
                                                        <i className="bi bi-check2-circle text-primary me-2"></i>
                                                        <span className="small fw-bold text-dark">{text}</span>
                                                    </div>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                </section>

                                {/* Section 3 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>3</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">Data Security</h3>
                                    </div>
                                    <p className="text-secondary ps-5" style={{ lineHeight: '1.8' }}>
                                        We utilize industry-standard 256-bit encryption. Our systems undergo regular security audits
                                        to protect against unauthorized access, alteration, or destruction of your personal data.
                                    </p>
                                </section>

                                {/* Contact Box */}
                                <section className="p-4 bg-primary-subtle bg-opacity-10 border border-primary-subtle rounded-4 mb-5">
                                    <h5 className="fw-black text-dark mb-3">Questions?</h5>
                                    <p className="small text-muted mb-4">Our legal team is here to help you understand your rights.</p>
                                    <div className="d-flex flex-column flex-md-row gap-4">
                                        <div className="d-flex align-items-center">
                                            <div className="bg-white shadow-sm rounded-circle p-2 me-3">
                                                <i className="bi bi-envelope-at text-primary"></i>
                                            </div>
                                            <span className="fw-bold text-dark small">legal@evently.com</span>
                                        </div>
                                        <div className="d-flex align-items-center">
                                            <div className="bg-white shadow-sm rounded-circle p-2 me-3">
                                                <i className="bi bi-geo-alt text-primary"></i>
                                            </div>
                                            <span className="fw-bold text-dark small">Sofia, Bulgaria</span>
                                        </div>
                                    </div>
                                </section>

                                <div className="text-center pt-4 border-top">
                                    <button
                                        className="btn btn-dark rounded-pill px-5 py-2 fw-bold hover-lift shadow-sm"
                                        onClick={() => window.history.back()}
                                    >
                                        <i className="bi bi-arrow-left me-2"></i>
                                        Go Back
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="py-5"></div>
        </div>
    );
};

export default PrivacyPolicy;