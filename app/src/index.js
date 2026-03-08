import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from "react-router-dom";

const stripePromise = loadStripe('pk_test_51T2p6MAsx12C9RhondmuulRPYTAdtqnWz0xudC8PZqa7vYPZvwqTX9UUtik2Ei5XdWip3sEh7y1VfLrHMFYjFC6T00ZGkda2p9');

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Elements stripe={stripePromise}>
                <App />
            </Elements>
        </BrowserRouter>
    </React.StrictMode>
);

reportWebVitals();