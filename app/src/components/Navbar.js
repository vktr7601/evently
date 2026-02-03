import React from 'react';
import { NavLink } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav style={navStyle}>
            <div style={logoStyle}>Evently</div>
            <ul style={linkListStyle}>
                <li>
                    <NavLink to="/events" style={navLinkStyle} activeStyle={activeLinkStyle}>
                        Explore Events
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/performers" style={navLinkStyle} activeStyle={activeLinkStyle}>
                        Performers
                    </NavLink>
                </li>

                <li>
                    <NavLink  onto="/categories" style={navLinkStyle} activeStyle={activeLinkStyle}>
                        Categories
                    </NavLink>
                </li>
            </ul>
        </nav>
    );
};

// Simple styles for layout
const navStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '1rem 2rem',
    background: '#ffffff',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
};

const logoStyle = {
    fontSize: '1.5rem',
    fontWeight: 'bold',
    color: '#2563eb',
};

const linkListStyle = {
    display: 'flex',
    listStyle: 'none',
    gap: '20px',
    margin: 0,
};

const navLinkStyle = {
    textDecoration: 'none',
    color: '#4b5563',
    fontWeight: '600',
};

const activeLinkStyle = {
    color: '#2563eb',
    borderBottom: '2px solid #2563eb',
};

export default Navbar;