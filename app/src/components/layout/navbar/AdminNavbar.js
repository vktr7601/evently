import { NavLink } from "react-router-dom";
import { ROUTES } from "../../../constants/routes";

const AdminNavbar = () => {
    return (
        <ul className="navbar-nav">
            <li className="nav-item">
                <NavLink to={ROUTES.ADMIN.DASHBOARD} className="nav-link px-3">Admin Dashboard</NavLink>
            </li>
        </ul>
    );
};

export default AdminNavbar;