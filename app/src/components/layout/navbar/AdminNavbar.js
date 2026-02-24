import { NavLink } from "react-router-dom";

const AdminNavbar = () => {
    return (
        <ul className="navbar-nav">
            <li className="nav-item">
                <NavLink to="/notifications" className="nav-link px-3">
                    Inbox
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink to="/orders" className="nav-link px-3">
                    Orders
                </NavLink>
            </li>

            <li className='nav-item'>
                <NavLink to="/events/create" className="nav-link px-3">Create Event</NavLink>
            </li>
            <li className="nav-item">
                <NavLink to="/admin/location/create" className="nav-link px-3">Create Location</NavLink>
            </li>
        </ul>
    );
};

export default AdminNavbar;