import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import EventsCard from './components/EventsCard'

// Dummy components for now
const EventsPage = () => <div style={{padding: '20px'}}><h2>All Events</h2><p>List of events here...</p></div>;
const PerformersPage = () => <div style={{padding: '20px'}}><h2>Our Performers</h2><p>List of artists here...</p></div>;
const Categories = () => <div style={{padding: '20px'}}><h2>Our Categories</h2><p>List of artists here...</p></div>;

const events = [{
    "name": "cool event"
}, {"name": "secondEvent"}]

function App() {
    return (
        <Router>
            <Navbar/>
            <Routes>
                <Route path="/events" element={<EventsPage/>}/>
                <Route path="/performers" element={<PerformersPage/>}/>
                <Route path="/categories" element={<Categories/>}/>
                {/* Default route */}
                <Route path="/" element={<EventsPage/>}/>
            </Routes>
            <EventsCard list={events}/>
            <Footer/>
        </Router>
    );
}

export default App;