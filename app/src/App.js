import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Navbar from './components/navbar/Navbar';
import Footer from './components/Footer';
import Home from './components/Home';
import Events from './components/event/Events';
import PrivacyPolicy from './components/PrivacyPolicy';
import Venues from './components/Venues';
import VenueDetails from './components/VenueDetails'
import Performers from './components/Performers'
import Register from './components/Register'

import 'bootstrap/dist/css/bootstrap.min.css';
import TermsOfService from "./components/TermsOfService";
import Login from './components/Login';
import Mail from './components/Mail'
import EventsAdmin from './components/EventsAdmin';
import CreatePerformerForm from './components/performer/CreatePerformerForm';

function App() {
    return (
        <div className="d-flex flex-column min-vh-100 bg-light">
            <Navbar />
            <main className="flex-grow-1 container py-5">
                <div className="row justify-content-center">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/performers" element={<Performers />} />
                        <Route path="/events" element={<Events />} />
                        <Route path="/privacy" element={<PrivacyPolicy />} />
                        <Route path="/terms" element={<TermsOfService />} />
                        <Route path="/venues" element={<Venues />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/venues/:venueId" element={<VenueDetails />} />
                        <Route path="/mail" element={<Mail />} />
                        <Route path="/admin/events" element={<EventsAdmin />} />
                        <Route path="/admin/performer" element= {<CreatePerformerForm />} />
                    </Routes>
                </div>
            </main>
            <Footer />
        </div>
    )
};

export default App;