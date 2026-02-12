import React from 'react';
import Hero from './Hero';

const TermsOfService = () => {
    return (
        <div className="bg-light min-vh-100">
            <Hero 
                badge="📜 Terms of Use"
                title="Service"
                highlight="Agreement"
                subtitle="Please read these terms carefully before using our platform. They outline your rights and our commitment to you."
                primaryAction={{ text: "Create Account", link: "/register" }}
                secondaryAction={{ text: "Privacy Policy", link: "/privacy" }}
            />

            {/* 2. Overlapping Card Content */}
            <div className="container" style={{ marginTop: "-60px", position: "relative", zIndex: "10" }}>
                <div className="row justify-content-center">
                    <div className="col-lg-10 col-xl-8">
                        <div className="card shadow-lg border-0 rounded-4 overflow-hidden mb-5">
                            <div className="card-body p-4 p-md-5">
                                
                                <header className="mb-5 d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3">
                                    <div>
                                        <span className="badge bg-primary-subtle text-primary rounded-pill px-3 py-2 mb-2 fw-bold" style={{ fontSize: '0.7rem' }}>
                                            LEGAL VERSION 2.0
                                        </span>
                                        <h2 className="fw-black text-dark mb-0 tracking-tight">Rules of the Road</h2>
                                    </div>
                                    <div className="text-md-end">
                                        <span className="text-muted small d-block">
                                            <i className="bi bi-calendar-check me-2"></i>
                                            Effective Date
                                        </span>
                                        <span className="fw-bold text-dark small">February 4, 2026</span>
                                    </div>
                                </header>

                                <hr className="my-5 opacity-10" />

                                {/* Section 1 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3 flex-shrink-0" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>1</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">Acceptance of Terms</h3>
                                    </div>
                                    <p className="text-secondary ps-md-5" style={{ lineHeight: '1.8' }}>
                                        By accessing and using <strong className="text-dark">Evently</strong>, you agree to 
                                        be bound by these Terms of Use and all applicable laws and regulations. If you do 
                                        not agree with any of these terms, you are prohibited from using or accessing this site.
                                    </p>
                                </section>

                                {/* Section 2 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3 flex-shrink-0" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>2</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">User Responsibilities</h3>
                                    </div>
                                    <div className="ps-md-5">
                                        <p className="text-secondary mb-3">To ensure a safe environment for all event-goers, you agree to:</p>
                                        <ul className="list-unstyled">
                                            {[
                                                "Provide accurate information during registration.",
                                                "Use the platform only for lawful event-related purposes.",
                                                "Respect the intellectual property of performers and organizers."
                                            ].map((item, idx) => (
                                                <li key={idx} className="d-flex align-items-start mb-2 text-secondary">
                                                    <i className="bi bi-shield-check text-success me-3 mt-1"></i>
                                                    <span>{item}</span>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                </section>

                                {/* Section 3 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3 flex-shrink-0" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>3</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">Ticketing Policy</h3>
                                    </div>
                                    <p className="text-secondary ps-md-5" style={{ lineHeight: '1.8' }}>
                                        All ticket sales processed through Evently are subject to the specific refund 
                                        policies of the individual event organizers. Evently acts as a facilitator and is 
                                        not responsible for event cancellations or schedule changes.
                                    </p>
                                </section>

                                {/* Section 4 */}
                                <section className="mb-5">
                                    <div className="d-flex align-items-center mb-3">
                                        <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center me-3 flex-shrink-0" style={{ width: '32px', height: '32px', fontSize: '0.8rem' }}>4</div>
                                        <h3 className="h5 fw-black text-dark mb-0 tracking-tight">Limitation of Liability</h3>
                                    </div>
                                    <div className="ps-md-5">
                                        <div className="p-4 bg-light border-start border-primary border-4 rounded-3 shadow-sm">
                                            <p className="text-dark mb-0 fst-italic">
                                                "Evently shall not be held liable for any indirect, incidental, or consequential 
                                                damages resulting from the use or inability to use our event services."
                                            </p>
                                        </div>
                                    </div>
                                </section>

                                <div className="text-center pt-5 border-top">
                                    <button
                                        className="btn btn-primary rounded-pill px-5 py-3 fw-bold shadow-sm hover-lift"
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
            <div className="py-5"></div>
        </div>
    );
};

export default TermsOfService;