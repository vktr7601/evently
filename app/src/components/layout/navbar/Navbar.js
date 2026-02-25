import React, { useEffect } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import AuthNavbar from './AuthNavbar';
import AdminNavbar from './AdminNavbar';

const Navbar = () => {
  const navigate = useNavigate();
  const [isAuth] = useState(localStorage.getItem("jwtToken") ? true : false);
  const [userRole] = useState(localStorage.getItem("userRole") || "");

  const handleSignOut = () => {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("userRole");
    navigate("/login");
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
              <NavLink to="/events" className="nav-link px-3">Events</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/artists" className="nav-link px-3">Artists</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/locations" className="nav-link px-3">Locations</NavLink>
            </li>
            {!isAuth && (
              <li className="nav-item">
                <Link to="/login" className="btn btn-primary rounded-pill px-4 text-white">
                  Sign In
                </Link>
              </li>
            )}

            {isAuth && (
              <AuthNavbar />
            )}
            {isAuth && userRole === "ADMIN" && (
              <AdminNavbar />
            )}
          </ul>
        </div>
      </div>
    </nav >
  );
};

export default Navbar;