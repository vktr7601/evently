import { NavLink } from "react-router-dom";
import { ROUTES } from "../../../constants/routes";

const AuthNavbar = () => {
    return (
        <ul className="navbar-nav">
            <li className="nav-item">
                <NavLink to={ROUTES.AUTH.PROFILE} className="nav-link px-3">
                    My Profile
                </NavLink>
            </li>
        </ul>
    );
};

export default AuthNavbar;