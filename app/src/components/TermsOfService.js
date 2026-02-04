import React from 'react';

const TermsOfService = () => {
    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-lg-9 col-xl-8">
                    {}
                    <div className="card shadow-sm border-0 p-4 p-md-5">
                        <div className="card-body">
                            <header className="mb-5">
                                <h1 className="fw-bold text-dark display-5 mb-3">Terms of Use</h1>
                                <div className="d-flex align-items-center">
                                    <span
                                        className="badge bg-primary-subtle text-primary border border-primary-subtle px-3 py-2 rounded-pill">
                                        Legal Version 2.0
                                    </span>
                                    <span className="text-muted ms-3 small">
                                        <i className="bi bi-calendar-check me-1"></i>
                                        Effective Date: February 4, 2026
                                    </span>
                                </div>
                            </header>

                            <hr className="my-5 opacity-10"/>


                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">1. Acceptance of Terms</h3>
                                <p className="text-secondary leading-relaxed">
                                    By accessing and using <strong className="text-dark">Evently</strong>, you agree to
                                    be bound by these Terms of Use and all applicable laws and regulations. If you do
                                    not agree with any of these terms, you are prohibited from using or accessing this
                                    site.
                                </p>
                            </section>

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">2. User Responsibilities</h3>
                                <p className="text-secondary">To ensure a safe environment for all event-goers, you
                                    agree to:</p>
                                <ul className="list-group list-group-flush border-0">
                                    <li className="list-group-item ps-0 bg-transparent text-secondary border-0">
                                        <i className="bi bi-shield-check text-success me-2"></i>
                                        Provide accurate information during registration.
                                    </li>
                                    <li className="list-group-item ps-0 bg-transparent text-secondary border-0">
                                        <i className="bi bi-shield-check text-success me-2"></i>
                                        Use the platform only for lawful event-related purposes.
                                    </li>
                                    <li className="list-group-item ps-0 bg-transparent text-secondary border-0">
                                        <i className="bi bi-shield-check text-success me-2"></i>
                                        Respect the intellectual property of performers and organizers.
                                    </li>
                                </ul>
                            </section>

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">3. Ticketing Policy</h3>
                                <p className="text-secondary">
                                    All ticket sales processed through Evently are subject to the specific refund
                                    policies of the individual event organizers. Evently acts as a facilitator and is
                                    not responsible for event cancellations or schedule changes.
                                </p>
                            </section>

                            <section className="mb-5">
                                <h3 className="h4 fw-bold text-primary mb-3">4. Limitation of Liability</h3>
                                <div className="p-4 bg-light border-start border-primary border-4 rounded-3">
                                    <p className="text-dark mb-0 italic">
                                        "Evently shall not be held liable for any indirect, incidental, or consequential
                                        damages resulting from the use or inability to use our event services."
                                    </p>
                                </div>
                            </section>

                            <div className="pt-4 border-top mt-5">
                                <button
                                    className="btn btn-primary rounded-pill px-5 shadow-sm"
                                    onClick={() => window.history.back()}
                                >
                                    I Understand & Return
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TermsOfService;