import React from 'react';

const PrivacyPolicy = () => {
    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-lg-9 col-xl-8">
                    <div className="card shadow-sm border-0 p-4 p-md-5">
                        <div className="card-body">
                            <header className="mb-5">
                                <h1 className="fw-bold text-dark display-5 mb-3">Privacy Policy</h1>
                                <div className="d-flex align-items-center">
                                    <span className="badge bg-primary-subtle text-primary border border-primary-subtle px-3 py-2 rounded-pill">
                                        v1.2 Release
                                    </span>
                                    <span className="text-muted ms-3 small">
                                        <i className="bi bi-clock me-1"></i>
                                        Last Updated: February 4, 2026
                                    </span>
                                </div>
                            </header>

                            <hr className="my-5 opacity-10" />

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">1. Information We Collect</h3>
                                <p className="text-secondary leading-relaxed">
                                    At <strong className="text-dark">Evently</strong>, we collect information to provide better services to our users.
                                    This includes information you provide to us (such as your name and email when signing up)
                                    and information we get from your use of our services (such as event preferences).
                                </p>
                            </section>

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">2. How We Use Information</h3>
                                <p className="text-secondary">We use the information we collect to facilitate the best experience possible:</p>
                                <ul className="list-group list-group-flush border-0">
                                    <li className="list-group-item ps-0 bg-transparent text-secondary border-0">
                                        <i className="bi bi-check-circle-fill text-success me-2"></i>
                                        Provide, maintain, and improve our services.
                                    </li>
                                    <li className="list-group-item ps-0 bg-transparent text-secondary border-0">
                                        <i className="bi bi-check-circle-fill text-success me-2"></i>
                                        Process your ticket bookings and registrations.
                                    </li>
                                    <li className="list-group-item ps-0 bg-transparent text-secondary border-0">
                                        <i className="bi bi-check-circle-fill text-success me-2"></i>
                                        Send you updates, security alerts, and support messages.
                                    </li>
                                </ul>
                            </section>

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">3. Data Security</h3>
                                <p className="text-secondary">
                                    We work hard to protect Evently and our users from unauthorized access to or unauthorized
                                    alteration, disclosure, or destruction of information we hold. We use industry-standard
                                    encryption and security practices.
                                </p>
                            </section>

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">4. Contact Us</h3>
                                <p className="text-secondary mb-4">
                                    If you have any questions about this Privacy Policy, please reach out to our legal team:
                                </p>
                                <div className="p-4 bg-light border-0 rounded-4 shadow-sm">
                                    <div className="d-flex align-items-center mb-2">
                                        <i className="bi bi-envelope-fill text-primary me-3 fs-5"></i>
                                        <span className="fw-semibold">support@evently.com</span>
                                    </div>
                                    <div className="d-flex align-items-center">
                                        <i className="bi bi-geo-alt-fill text-primary me-3 fs-5"></i>
                                        <span className="text-muted">123 Event Ave, Tech City</span>
                                    </div>
                                </div>
                            </section>

                            <div className="pt-4 border-top mt-5">
                                <button
                                    className="btn btn-outline-secondary rounded-pill px-4"
                                    onClick={() => window.history.back()}
                                >
                                    <i className="bi bi-arrow-left me-2"></i>
                                    Return to Previous Page
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PrivacyPolicy;