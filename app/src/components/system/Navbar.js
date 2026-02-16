import React, { useEffect } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';

const Navbar = () => {
  const navigate = useNavigate();
  // const isAuthenticated = !!localStorage.getItem("userToken");
  const isAuth = true;
  const [activeOrder, setActiveOrder] = useState(false);

  const handleSignOut = () => {
    localStorage.removeItem("userToken");
    navigate("/login");
  };

  useEffect(() => {
    const headers = { "X-User-Id": 1 };
    axios.get(`http://localhost:8081/orders/active`, { headers })
      .then(res => {
        console.log(res.data);
        if(res.status === 200) {
          setActiveOrder(true);
        } else if (res.status === 204) {
          console.log("No active order found (Status 204).");
          setActiveOrder(false);
        }
        if (res.status === 204) {
          console.log("No active order found (Status 204).");
          setActiveOrder(false);
          return; // Stop here since there is no data to set
        }
      })
      .catch(err => {
        console.error("Error fetching order:", err);
      });
  }, []);


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
            {isAuth && (
              <li className="nav-item">
                <NavLink to="/notifications" className="nav-link px-3">Inbox</NavLink>
              </li>
            )}
            <li className="nav-item">
              <NavLink to="/locations" className="nav-link px-3">Locations</NavLink>
            </li>
            {isAuth ? (
              <li className="nav-item" >
                <NavLink to="/profile" className="nav-link px-3">My Profile</NavLink>
              </li>
            ) : (
              <Link to="/login" className="btn btn-primary rounded-pill px-4 text-white">
                Sign In
              </Link>
            )}
            {activeOrder && (
              <li className="nav-item"> {/* <--- Missing Opening Tag */}
                <NavLink
                  to="/order/active"
                  className="btn btn-outline-primary rounded-pill px-4 shadow-sm fw-bold d-flex align-items-center gap-2"
                >
                  <span style={{
                    width: '8px',
                    height: '8px',
                    backgroundColor: '#10b981',
                    borderRadius: '50%',
                    display: 'inline-block'
                  }}></span>
                  Active Order
                </NavLink>
              </li>
            )}

          </ul>
        </div>
      </div>
    </nav >
  );
};

export default Navbar;