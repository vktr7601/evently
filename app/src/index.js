import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from "react-router-dom";

// 1. Initialize Stripe with your Public Key (Starts with pk_)
const stripePromise = loadStripe('pk_test_51T2p6MAsx12C9Rhodt6zM53I6WuYVGweUxeeCtAG1eczzxc9p8s1ZteOgSClcMCyhm2UDPQiyaLOmuHdl1FfIBuK00ysHnxwYe');

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <BrowserRouter>
            {/* 2. Wrap App with Elements so any component inside can use Stripe hooks */}
            <Elements stripe={stripePromise}>
                <App />
            </Elements>
        </BrowserRouter>
    </React.StrictMode>
);

reportWebVitals();