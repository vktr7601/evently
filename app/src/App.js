import { Route, Routes } from 'react-router-dom';
import Navbar from './components/layout/navbar/Navbar';
import Footer from './components/layout/Footer';
import Home from './components/system/Home';
import Events from './features/events/Events';
import Locations from './features/locations/Locations';
import PrivacyPolicy from './components/system/PrivacyPolicy';
import TermsOfService from './components/system/TermsOfService';
import Login from './features/auth/Login';
import Register from './features/auth/Register';
import LocationDetails from './features/locations/LocationDetails';
import Artists from './features/artists/Artists';
import ArtistDetails from './features/artists/ArtistDetais';
import EventDetails from './features/events/EventDetails';
import EditEvent from './features/events/EditEvent';
import Notifications from './features/notifications/Notifications';
import Profile from './features/auth/Profile';
import CreateEvent from './features/events/CreateEvent';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      <Navbar />
      <main className="flex-grow-1 container py-5">
        <div className="row justify-content-center">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/events" element={<Events />} />
            <Route path="/events/:id" element={<EventDetails />} />
            <Route path="/privacy" element={<PrivacyPolicy />} />
            <Route path="/terms" element={<TermsOfService />} />
            <Route path="/locations" element={<Locations />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/locations/:id" element={<LocationDetails />} />
            <Route path="/artists" element={<Artists />} />
            <Route path="/artists/:id" element={<ArtistDetails />} />
            <Route path="/events/:id/edit" element={<EditEvent />} />
            <Route path="/notifications" element={<Notifications />} />
            <Route path="/profile" element={< Profile />} />
             <Route path="/admin/events" element={<CreateEvent />} />
            {/* <Route path="/artists" element={<Artists />} />
            <Route path="/artists/:id" element={<ArtistDetails />} />
            <Route path="/events/:id" element={<EventDetails />} />
            <Route path="/events/create" element={<CreateEvent />} />
            <Route path="/notifications" element={<Notifications />} />
            <Route path="/profile" element={< Profile />} />
            <Route path='/event-details/:id' element={<EventLocationDetails />} />
            <Route path="/order/active" element={<OrderPayment />} />
            <Route path="/orders" element={<Orders />} />
            <Route path="/orders/details/:number" element={<OrderDetails />} />
            <Route path="/admin/event-management/:id" element={<EditEvent />} />
            <Route path="/admin/location/create" element={<CreateLocation />} /> */} */} */}
          </Routes>
        </div>
      </main>
      <Footer />
    </div>
  )
};

export default App;