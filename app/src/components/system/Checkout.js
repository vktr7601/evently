import React, { useState } from 'react';

const Checkout = ({ event, quantity = 1, onPurchase }) => {
  const [loading, setLoading] = useState(false);
  const ticketPrice = event?.price || 25.00;
  const serviceFee = 2.50;
  const total = (ticketPrice * quantity) + serviceFee;

  const handleCheckout = async (e) => {
    e.preventDefault();
    setLoading(true);
    // Simulate API call to your Ticket Service
    setTimeout(() => {
      setLoading(false);
      onPurchase();
    }, 2000);
  };

  return (
    <div className="container py-5">
      <div className="row g-5">
        {/* Left Side: Payment Form */}
        <div className="col-md-7 col-lg-8">
          <h4 className="mb-4 fw-black">Payment Details</h4>
          <form className="needs-validation" onSubmit={handleCheckout}>
            <div className="row g-3">
              <div className="col-12">
                <label className="form-label text-muted small fw-bold">CARDHOLDER NAME</label>
                <input type="text" className="form-control form-control-lg border-0 shadow-sm" placeholder="John Doe" required />
              </div>

              <div className="col-12">
                <label className="form-label text-muted small fw-bold">CARD NUMBER</label>
                <input type="text" className="form-control form-control-lg border-0 shadow-sm" placeholder="**** **** **** 1234" required />
              </div>

              <div className="col-md-6">
                <label className="form-label text-muted small fw-bold">EXPIRATION</label>
                <input type="text" className="form-control form-control-lg border-0 shadow-sm" placeholder="MM/YY" required />
              </div>

              <div className="col-md-6">
                <label className="form-label text-muted small fw-bold">CVV</label>
                <input type="text" className="form-control form-control-lg border-0 shadow-sm" placeholder="123" required />
              </div>
            </div>

            <hr className="my-4 opacity-10" />

            <button
              className="w-100 btn btn-primary btn-lg rounded-pill shadow-sm fw-bold py-3"
              type="submit"
              disabled={loading}
            >
              {loading ? (
                <span className="spinner-border spinner-border-sm me-2"></span>
              ) : `Pay $${total.toFixed(2)}`}
            </button>
          </form>
        </div>

        {/* Right Side: Order Summary */}
        <div className="col-md-5 col-lg-4">
          <div className="card border-0 shadow-sm rounded-4 p-4 bg-light">
            <h4 className="d-flex justify-content-between align-items-center mb-3">
              <span className="text-primary fw-black">Your Order</span>
              <span className="badge bg-primary rounded-pill">{quantity}</span>
            </h4>
            <ul className="list-group list-group-flush mb-3">
              <li className="list-group-item d-flex justify-content-between lh-sm bg-transparent border-0 px-0">
                <div>
                  <h6 className="my-0 fw-bold">{event?.title || 'Midnight Jazz Session'}</h6>
                  <small className="text-muted">General Admission</small>
                </div>
                <span className="text-muted">${ticketPrice.toFixed(2)}</span>
              </li>
              <li className="list-group-item d-flex justify-content-between bg-transparent border-0 px-0">
                <div className="text-muted">
                  <small>Service Fee</small>
                </div>
                <span className="text-muted">${serviceFee.toFixed(2)}</span>
              </li>
              <li className="list-group-item d-flex justify-content-between bg-transparent px-0">
                <span className="fw-black">Total (USD)</span>
                <strong className="text-primary">${total.toFixed(2)}</strong>
              </li>
            </ul>

            <div className="p-2 bg-white rounded-3 mt-2 border border-dashed text-center">
              <small className="text-muted">Tickets will be sent to your email after payment.</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Checkout;