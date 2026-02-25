    import { Link } from 'react-router-dom';

    const Hero = ({ title, highlight, subtitle, primaryAction, secondaryAction, badge }) => {
        return (
            <section className="position-relative overflow-hidden py-5 mb-5 hero-gradient">
                {/* Subtle Background Accent */}
                <div className="hero-accent position-absolute top-0 start-50 translate-middle-x w-100 h-100 opacity-25"></div>

                <div className="container position-relative py-5" style={{ zIndex: 1 }}>
                    <div className="row justify-content-center text-center">
                        <div className="col-lg-10">
                            {badge && (
                                <span className="badge rounded-pill bg-primary-subtle text-primary mb-3 px-3 py-2 text-uppercase fw-semibold tracking-wider">
                                    {badge}
                                </span>
                            )}

                            <h1 className="display-2 fw-black text-dark mb-4 tracking-tight">
                                {title} <span className="text-primary position-relative">
                                    {highlight}
                                    <svg className="position-absolute start-0 bottom-0 w-100 underline-svg" viewBox="0 0 100 20" preserveAspectRatio="none">
                                        <path d="M0,15 Q50,5 100,15" stroke="#0d6efd" strokeWidth="4" fill="transparent" strokeLinecap="round" />
                                    </svg>
                                </span>
                            </h1>
                            
                            <p className="fs-5 text-secondary mb-5 mx-auto max-w-650">
                                {subtitle}
                            </p>
                            
                            <div className="d-flex flex-column flex-sm-row justify-content-center gap-3">
                                <Link to={primaryAction.link} className="btn btn-primary btn-lg rounded-pill px-5 py-3 shadow hover-lift">
                                    {primaryAction.text}
                                </Link>
                                <Link to={secondaryAction.link} className="btn btn-white btn-lg rounded-pill px-5 py-3 border shadow-sm hover-lift">
                                    {secondaryAction.text}
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        );
    };

    export default Hero;