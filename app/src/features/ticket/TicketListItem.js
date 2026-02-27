import React, { useState } from 'react';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const TicketListItem = ({ ticket, onRefund }) => {
    const [isAdmin] = useState(localStorage.getItem("userRole") === "ADMIN");
    const formatDateTime = (dateString) => {
        return new Date(dateString).toLocaleString(undefined, {
            weekday: 'short',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    const handleRequest = (id) => {
        axiosClient.post(`${ROUTES.TICKETS.REFUND(id)}`)
            .then(res => {
                const htmlString = res.data;

                const newWindow = window.open('', '_blank');
                newWindow.document.write(htmlString);
                newWindow.document.close();
            })
            .catch(err => console.error(err));
    };

    const viewTicket = () => {
        axiosClient.get(`${ROUTES.TICKETS.VIEW(ticket.id)}`)
            .then(res => {
                const htmlString = res.data;

                const newWindow = window.open('', '_blank');
                newWindow.document.write(htmlString);
                newWindow.document.close();
            })
            .catch(err => console.error(err));
    }

    const isCanceled = ticket.status === 'CANCELED';

    return (
        <div className={`card border-0 shadow-sm rounded-4 p-4 mb-3 ${isCanceled ? 'opacity-75' : ''}`}>
            <div className="row align-items-center g-4">

                <div className="col-md-4 border-end-md">
                    <h5 className={`fw-bold mb-1 ${isCanceled ? 'text-muted' : 'text-primary'}`}>
                        {ticket.eventName}
                    </h5>
                    <div className="text-secondary small fw-medium">
                        <i className="bi bi-geo-alt me-1"></i>
                        {ticket.eventLocationName}
                    </div>
                </div>

                <div className="col-md-3 border-end-md">
                    <label className="text-muted small fw-bold d-block mb-1 text-uppercase">
                        Date And Time
                    </label>
                    <h6 className="fw-bold mb-0">
                        {formatDateTime(ticket.eventStartTime)}
                    </h6>
                </div>
                
                <div className="col-md-2 text-md-end">
                    {!isCanceled && (
                        <button onClick={viewTicket} className="btn btn-light border rounded-pill px-4 py-2 fw-bold w-100 transition-hover">
                            <i className="bi bi-download me-2"></i>
                            View Ticket
                        </button>
                    )}
                </div>
                
                {/* {Todo: implemnt} */}
                <div className="col-md-3 text-md-end">
                    {isCanceled ? (
                        <span className="badge bg-danger-subtle text-danger rounded-pill px-4 py-2 fs-6 w-100">
                            CANCELED
                        </span>
                    ) : (
                        <button
                            className="btn btn-outline-danger btn-lg rounded-pill px-4 fw-bold w-100"
                            onClick={() => handleRequest(ticket.id)}
                        >
                            Refund Ticket
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
};

export default TicketListItem;