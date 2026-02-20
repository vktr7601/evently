import React, { useEffect } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';

const Navbar = () => {
  const navigate = useNavigate();
  // const isAuthenticated = !!localStorage.getItem("userToken");
  const isAuth = false;
  const handleSignOut = () => {
    localStorage.removeItem("userToken");
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
            <li className='nav-item'>
              <NavLink to="/events/create" className="nav-link px-3">Create Event</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/artists" className="nav-link px-3">Artists</NavLink>
            </li>
            {isAuth && (
              <li className="nav-item">
                <NavLink to="/notifications" className="nav-link px-3">Inbox</NavLink>
              </li>
            )}
            <li className="nav-item">
              <NavLink to="/locations" className="nav-link px-3">Locations</NavLink>
            </li>
             <li className="nav-item">
              <NavLink to="/admin/location/create" className="nav-link px-3">Create Location</NavLink>
            </li>
            {<li className="nav-item">
              <NavLink to="/orders" className="nav-link px-3">Orders</NavLink>
            </li>}
            {isAuth ? (
              <li className="nav-item" >
                <NavLink to="/profile" className="nav-link px-3">My Profile</NavLink>
              </li>
            ) : (
              <Link to="/login" className="btn btn-primary rounded-pill px-4 text-white">
                Sign In
              </Link>
            )}
          </ul>
        </div>
      </div>
    </nav >
  );
};

export default Navbar;