import React from 'react';
import {Link, NavLink} from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top">
      <div className="container">
        <Link className="navbar-brand d-flex align-items-center" to="/">
          <img
            src="https://evently-spring.s3.eu-north-1.amazonaws.com/Gemini_Generated_Image_uxcs1zuxcs1zuxcs.png"
            alt="Logo"
            className="brand-logo-simple"
          />
          <span className="fw-bold ms-2">Evently</span>
        </Link>

        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink to="/events" className="nav-link px-3">Events</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/register" className="nav-link px-3">Register</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/performers" className="nav-link px-3">Performers</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/venues" className="nav-link px-3">Venues</NavLink>
            </li>
            <li className="nav-item ms-lg-3">
              <Link to="/login" className="btn btn-primary rounded-pill px-4">Sign In</Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;