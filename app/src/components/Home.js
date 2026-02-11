import { Link } from 'react-router-dom';
import EventList from '../components/eventList/EventList';


const Home = () => {
  return (
    <div className="home-wrapper bg-white min-vh-100">
      
      <section className="py-5 mb-5 border-bottom bg-light bg-gradient">
        <div className="container py-5">
          <div className="row justify-content-center text-center">
            <div className="col-lg-8">
              
              <h1 className="display-3 fw-bold text-dark mb-4">
                Discover Events <span className="text-primary">That Matter.</span>
              </h1>
              
              <p className="lead text-muted mb-5 px-md-5">
                From local concerts to global tech summits, Evently connects you
                with the experiences you've been waiting for. 
                <strong> Secure your spot today.</strong>
              </p>
              
              <div className="d-flex justify-content-center gap-3">
                <Link to="/events" className="btn btn-primary btn-lg rounded-pill px-5 shadow-sm">
                  Browse Events
                </Link>
                <Link to="/about" className="btn btn-outline-dark btn-lg rounded-pill px-5">
                  Learn More
                </Link>
              </div>

            </div>
          </div>
        </div>
      </section>

      {/* --- MAIN CONTENT --- */}
      <main className="pb-5">
        <div className="container">
          <div className="row mb-5">
            <div className="col-12 text-center">
              <h6 className="text-primary fw-bold text-uppercase mb-2" style={{ letterSpacing: '1px' }}>
                Featured
              </h6>
              <h2 className="fw-bold h1">Upcoming This Week</h2>
              <div className="mx-auto bg-primary mb-4" style={{ height: '3px', width: '50px' }}></div>
            </div>
          </div>
          
          {/* Your EventList Component */}
          <EventList />
        </div>
      </main>
    </div>
  );
};

export default Home;