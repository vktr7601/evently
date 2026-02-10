import React from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  const navigate = useNavigate();
  const isAuthenticated = !!localStorage.getItem("userToken");

  const handleSignOut = () => {
    localStorage.removeItem("userToken");
    navigate("/login");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top">
      <div className="container">
        <Link className="navbar-brand d-flex align-items-center" to="/">
          <img
            src="https://evently-spring.s3.eu-north-1.amazonaws.com/Gemini_Generated_Image_uxcs1zuxcs1zuxcs.png"
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
              <NavLink to="/performers" className="nav-link px-3">Performers</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/venues" className="nav-link px-3">Venues</NavLink>
            </li>


            {isAuthenticated ? (
              <>
                <li className="nav-item">
                  <NavLink to="/mail" className="nav-link px-3">Mail</NavLink>
                </li>
                <li className="nav-item ms-lg-3">
                  <button className="btn btn-dark rounded-pill px-4" onClick={handleSignOut}>Sign Out</button>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item">
                  <Link to="/login" className="btn btn-primary rounded-pill px-4 text-white">
                    Sign In
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;