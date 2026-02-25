import EventList from '../../features/events/EventList'
import Hero from '../layout/Hero';

const Home = () => {
    return (
        <div className="home-wrapper bg-white min-vh-100">
            <Hero 
                badge="🎉 Your Next Memory Starts Here"
                title="Discover Events"
                highlight="That Matter."
                subtitle={<>Join a community of enthusiasts. From <span className="text-dark fw-medium">intimate concerts</span> to <span className="text-dark fw-medium">global tech summits</span>, Evently is your gateway.</>}
                primaryAction={{ text: "Browse Events", link: "/events" }}
                secondaryAction={{ text: "Learn More", link: "/about" }}
            />

            <main className="pb-5">
                <div className="container">
                    <header className="text-center mb-5">
                        <h6 className="text-primary fw-bold text-uppercase tracking-wider mb-2">Featured</h6>
                        <h2 className="fw-black display-5 mb-3">Upcoming This Week</h2>
                        <div className="mx-auto bg-primary rounded" style={{ height: '4px', width: '60px' }}></div>
                    </header>
                    
                    <EventList />
                </div>
            </main>
        </div>
    );
};

export default Home;