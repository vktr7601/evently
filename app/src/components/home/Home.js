import EventList from "../eventList/EventList";
import {Link} from "react-router-dom";

const Home = () => {
    return (
        <div className="home-wrapper">
            <section className="bg-white py-5 mb-5 border-bottom">
                <div className="container py-lg-5">
                    <div className="row align-items-center">
                        <div className="col-lg-6">
                            <h1 className="display-3 fw-bold text-dark mb-3">
                                Discover Events <br/>
                                <span className="text-primary">That Matter.</span>
                            </h1>
                            <p className="lead text-muted mb-4">
                                From local concerts to global tech summits, Evently connects you
                                with the experiences you've been waiting for. Secure your spot today.
                            </p>
                            <div className="d-flex gap-3">
                                <Link to="/events" className="btn btn-primary btn-lg rounded-pill px-4 shadow-sm">
                                    Browse Events
                                </Link>
                                <Link to="/about" className="btn btn-outline-secondary btn-lg rounded-pill px-4">
                                    Learn More
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <main>
                <div className="container">
                    <div className="row mb-4">
                        <div className="col-12 text-center">
                            <span className="text-primary fw-bold text-uppercase tracking-wider">Top Picks</span>
                            <h2 className="fw-bold display-5">Featured This Week</h2>
                        </div>
                    </div>
                    <EventList/>
                </div>
            </main>
        </div>
    );
};

export default Home;