import { Route, Routes } from 'react-router-dom';

import Navbar from './components/system/Navbar';
import Footer from './components/system/Footer';
import Home from './components/system/Home';
import Events from './pages/event/Events';
import PrivacyPolicy from './components/system/PrivacyPolicy';

import Register from './components/system/Register'

import 'bootstrap/dist/css/bootstrap.min.css';
import TermsOfService from "./components/system/TermsOfService";
import Login from './components/system/Login';
import Artists from './pages/artist/Artists'
import ArtistDetails from './pages/artist/ArtistDetais'
import Locations from './pages/location/Locations'
import EventDetails from './pages/event/EventDetails';
import LocationDetails from './pages/location/LocationDetails';
import NotificationsPage from './pages/notifications/ NotificationsPage';
import ProfilePage from './components/system/Profile';
import EventLocationDetails from './pages/event/EventLocationDetails';
import OrderPayment from './pages/payment/OrderPayment';
import Orders from './pages/order/Orders';
import OrderDetails from './pages/order/OrderDetails';
import CreateEvent from './pages/event/CreateEvent';
import EditEvent from './pages/event/EditEvent';
import CreateLocation from './pages/location/CreateLocation';

function App() {
  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      <Navbar />
      <main className="flex-grow-1 container py-5">
        <div className="row justify-content-center">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/events" element={<Events />} />
            <Route path="/privacy" element={<PrivacyPolicy />} />
            <Route path="/terms" element={<TermsOfService />} />
            <Route path="/locations" element={<Locations />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
            <Route path="/locations/:id" element={<LocationDetails />} />
            <Route path="/artists" element={<Artists />} />
            <Route path="/artists/:id" element={<ArtistDetails />} />
            <Route path="/events/:id" element={<EventDetails />} />
            <Route path="/events/create" element={<CreateEvent />} />
            <Route path="/notifications" element={<NotificationsPage />} />
            <Route path="/profile" element={< ProfilePage />} />
            <Route path='/event-details/:id' element={<EventLocationDetails />} />
            <Route path="/order/active" element={<OrderPayment />} />
            <Route path="/orders" element={<Orders />} />
            <Route path="/orders/details/:number" element={<OrderDetails />} />
            <Route path="/admin/event-management/:id" element={<EditEvent />} />
            <Route path="/admin/location/create" element={<CreateLocation />} />
          </Routes>
        </div>
      </main>
      <Footer />
    </div>
  )
};

export default App;