import { NavLink } from "react-router-dom";
import { ROUTES } from "../../../constants/routes";

const AdminNavbar = () => {
    return (
        <ul className="navbar-nav">
            <li className="nav-item">
                <NavLink to={ROUTES.NOTIFICATIONS.BASE} className="nav-link px-3">
                    Inbox
                </NavLink>
            </li>
            {/* //adming orders */}
             <li className="nav-item">
                <NavLink to={ROUTES.ORDERS.ADMIN} className="nav-link px-3">
                    Orders
                </NavLink>
            </li>
            <li className='nav-item'>
                <NavLink to={ROUTES.EVENTS.ADMIN_CREATE} className="nav-link px-3">Create Event</NavLink>
            </li>
            <li className="nav-item">
                <NavLink to={ROUTES.LOCATIONS.ADMIN_CREATE} className="nav-link px-3">Create Location</NavLink>
            </li>
            <li className="nav-item">
                <NavLink to={ROUTES.ARTISTS.ADMIN_CREATE} className="nav-link px-3">Create Artist</NavLink>
            </li>
        </ul>
    );
};

export default AdminNavbar;