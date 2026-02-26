import React, { useEffect } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import AuthNavbar from './AuthNavbar';
import AdminNavbar from './AdminNavbar';
import { ROUTES } from '../../../constants/routes';

const Navbar = () => {
  const [isAuth] = useState(localStorage.getItem("jwtToken") ? true : false);
  const [userRole] = useState(localStorage.getItem("userRole") || "");
  const handleSignOut = () => {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("userRole");
    window.location.href = ROUTES.AUTH.LOGIN;
  };
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top">
      <div className="container">
        <Link className="navbar-brand d-flex align-items-center" to="/">
          <img
            src="https://evently-spring.s3.eu-north-1.amazonaws.com/internal/evently_logo.jpg"
            alt="Logo"
            className="brand-logo-simple"
            style={{ width: '40px', height: '40px' }}
          />
          <span className="fw-bold ms-2">Evently</span>
        </Link>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto align-items-center">
            <li className="nav-item">
              <NavLink to={ROUTES.EVENTS.BASE} className="nav-link px-3">Events</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to={ROUTES.ARTISTS.BASE} className="nav-link px-3">Artists</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to={ROUTES.LOCATIONS.BASE} className="nav-link px-3">Locations</NavLink>
            </li>
            {isAuth && userRole === "USER" && (
              <AuthNavbar />
            )}
            {isAuth && userRole === "ADMIN" && (
              <AdminNavbar />
            )}

            {!isAuth ? (
              <li className="nav-item">
                <Link to={ROUTES.AUTH.LOGIN} className="btn btn-primary rounded-pill px-4 text-white">
                  Sign In
                </Link>
              </li>
            ) : (
              <li className="nav-item">
                <button onClick={handleSignOut} className="btn btn-outline-secondary rounded-pill px-4 ms-3">
                  Sign Out
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav >
  );
};

export default Navbar;