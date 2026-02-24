import { NavLink } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const AuthNavbar = () => {
    const navigate = useNavigate();
    const handleSignOut = () => {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("userRole");
        navigate("/login");
    };
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

            <li className="nav-item">
                <NavLink to="/profile" className="nav-link px-3">
                    My Profile
                </NavLink>
            </li>
            <button onClick={handleSignOut} className="btn btn-outline-secondary rounded-pill px-4 ms-3">
                Sign Out
            </button>
        </ul>
    );
};

export default AuthNavbar;