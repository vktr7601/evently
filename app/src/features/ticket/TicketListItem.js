import React, { useState } from 'react';
import axiosClient from '../../api/axiosClient';
import { ROUTES } from '../../constants/routes';

const TicketListItem = ({ ticket }) => {
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
        axiosClient.post(`${ROUTES.REFUNDS.TICKET_REFUND(id)}`)
            .then(res => {
                const htmlString = res.data;

                const newWindow = window.open('', '_blank');
                newWindow.document.write(htmlString);
                newWindow.document.close();
            })
            .catch(err => console.error(err));
    };

    const handleViewTicket = () => {
        axiosClient.get(`${ROUTES.TICKETS.VIEW(ticket.id)}`)
            .then(res => {
                const htmlString = res.data;

                const newWindow = window.open('', '_blank');
                newWindow.document.write(htmlString);
                newWindow.document.close();
            })
            .catch(err => console.error(err));
    }

    const handleDownloadPDF = () => {
        axiosClient.get(`${ROUTES.TICKETS.PDF(ticket.id)}`, { responseType: 'blob' })
            .then(res => {
                const url = window.URL.createObjectURL(new Blob([res.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `ticket_${ticket.id}.pdf`);
                document.body.appendChild(link);
                link.click();
                link.remove();
            })
            .catch(err => console.error(err));
    }

    const checkIfTicketIsRefundable = (ticket) => {
        axiosClient.get(`${ROUTES.REFUNDS.TICKET_ELIGIBILITY(ticket.id)}`)
            .then(res => {
                const isEligible = res.data.isEligible;

                if (isEligible) {
                    const confirmed = window.confirm("Are you sure you want to refund this ticket? This action cannot be undone.");
                    if (confirmed) {
                        handleRequest(ticket.id);
                    }
                } else {
                    alert("This ticket is not refundable.");
                }
            })
            .catch(err => console.error(err));
        const refundableStatuses = ['COMPLETED', 'CONFIRMED'];
        console.log("Checking if ticket is refundable. Order status:", ticket.orderStatus, "Is refunded:", ticket.isRefunded);
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

                <div className="col-md-5     text-md-end">
                    {isCanceled ? (
                        <span className="badge bg-danger-subtle text-danger rounded-pill px-4 py-2 fs-6 w-100">
                            CANCELED
                        </span>
                    ) : (
                        <div className="d-flex gap-2">
                            <button
                                className="btn btn-outline-danger rounded-pill px-3 fw-bold"
                                onClick={() => checkIfTicketIsRefundable(ticket)}
                            >
                                Refund Ticket
                            </button>
                            <button
                                className="btn btn-outline-primary rounded-pill px-3 fw-bold"
                                onClick={() => handleDownloadPDF(ticket.id)}
                            >
                                Download PDF
                            </button>
                            <button
                                className="btn btn-outline-secondary rounded-pill px-3 fw-bold"
                                onClick={() => handleViewTicket(ticket.id)}
                            >
                                View Ticket
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default TicketListItem;