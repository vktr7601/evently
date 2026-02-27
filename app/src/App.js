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
import EventLocationDetails from './features/events/EventLocationDetails';
import OrderPayment from './features/payment/OrderPayment';
import Orders from './features/order/Orders';
import OrderDetails from './features/order/OrderDetails';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ROUTES } from './constants/routes';
import Tickets from './features/ticket/Tickets';
import CreateLocation from './features/locations/CreateLocation';
import CreateArtist from './features/artists/CreateArtist';
import CreateEventApi from './features/events/CreateEventApi';

function App() {
  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      <Navbar />
      <main className="flex-grow-1 container py-5">
        <div className="row justify-content-center">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path={ROUTES.EVENTS.BASE} element={<Events />} />
            <Route path={ROUTES.EVENTS.ADMIN_CREATE} element={<CreateEvent />} />
            <Route path="/events/:id" element={<EventDetails />} />
            <Route path="/privacy" element={<PrivacyPolicy />} />
            <Route path="/terms" element={<TermsOfService />} />
            <Route path={ROUTES.LOCATIONS.BASE} element={<Locations />} />
            <Route path={ROUTES.AUTH.LOGIN} element={<Login />} />
            <Route path={ROUTES.AUTH.REGISTER} element={<Register />} />
            <Route path={ROUTES.ARTISTS.BASE} element={<Artists />} />
            <Route path={ROUTES.ARTISTS.DETAILS(':id')} element={<ArtistDetails />} />
            <Route path={ROUTES.EVENTS.ADMIN_EDIT(':id')} element={<EditEvent />} />
            <Route path={ROUTES.NOTIFICATIONS.BASE} element={<Notifications />} />
            <Route path={ROUTES.AUTH.PROFILE} element={<Profile />} />
            <Route path={ROUTES.ORDERS.ACTIVE} element={<OrderPayment />} />
            <Route path={ROUTES.ORDERS.BASE} element={<Orders />} />
            <Route path={ROUTES.ORDERS.DETAILS(':number')} element={<OrderDetails />} />
            <Route path={ROUTES.TICKETS.USER} element={<Tickets />} />
            <Route path={ROUTES.LOCATIONS.DETAILS(':id')} element={<LocationDetails />} />
            <Route path={ROUTES.LOCATIONS.ADMIN_CREATE} element={<CreateLocation />} />
            <Route path={ROUTES.ARTISTS.ADMIN_CREATE} element={<CreateArtist />} />
            <Route path={ROUTES.EVENTS.EVENT_LOCATIONS_DETAILS(':id')} element={<EventLocationDetails />} />
            <Route path={ROUTES.EVENTS.ADMIN_BULK_CREATE} element={<CreateEventApi />} />
            {/* <Route path="/artists" element={<Artists />} />
            <Route path="/artists/:id" element={<ArtistDetails />} />
            <Route path="/events/:id" element={<EventDetails />} />
            <Route path="/events/create" element={<CreateEvent />} />
            <Route path="/notifications" element={<Notifications />} />
            <Route path="/profile" element={< Profile />} />
            <Route path='/event-details/:id' element={<EventLocationDetails />} />

            <Route path="/admin/event-management/:id" element={<EditEvent />} />
            <Route path="/admin/location/create" element={<CreateLocation />} /> */}
          </Routes>
        </div>
      </main>
      <Footer />
    </div>
  )
};

export default App;