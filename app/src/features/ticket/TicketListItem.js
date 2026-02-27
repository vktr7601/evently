import React from 'react';

const TicketListItem = ({ ticket, onRefund }) => {
    const formatDateTime = (dateString) => {
        return new Date(dateString).toLocaleString(undefined, {
            weekday: 'short', 
            month: 'short', 
            day: 'numeric', 
            hour: '2-digit', 
            minute: '2-digit'
        });
    };

    const isCanceled = ticket.status === 'CANCELED';

    return (
        <div className={`card border-0 shadow-sm rounded-4 p-4 mb-3 ${isCanceled ? 'opacity-75' : ''}`}>
            <div className="row align-items-center g-4">
                
                {/* 1. Event Info */}
                <div className="col-md-4 border-end-md">
                    <h5 className={`fw-bold mb-1 ${isCanceled ? 'text-muted' : 'text-primary'}`}>
                        {ticket.eventName}
                    </h5>
                    <div className="text-secondary small fw-medium">
                        <i className="bi bi-geo-alt me-1"></i>
                        {ticket.eventLocationName}
                    </div>
                </div>

                {/* 2. Schedule */}
                <div className="col-md-3 border-end-md">
                    <label className="text-muted small fw-bold d-block mb-1 text-uppercase">
                        Date And Time
                    </label>
                    <h6 className="fw-bold mb-0">
                        {formatDateTime(ticket.eventStartTime)}
                    </h6>
                </div>

                {/* 3. Download Action */}
                <div className="col-md-2 text-md-end">
                    {!isCanceled && (
                        <button className="btn btn-light border rounded-pill px-4 py-2 fw-bold w-100 transition-hover">
                            <i className="bi bi-download me-2"></i> PDF
                        </button>
                    )}
                </div>

                {/* 4. Conditional Status/Action */}
                <div className="col-md-3 text-md-end">
                    {isCanceled ? (
                        <span className="badge bg-danger-subtle text-danger rounded-pill px-4 py-2 fs-6 w-100">
                            CANCELED
                        </span>
                    ) : (
                        <button
                            className="btn btn-outline-danger btn-lg rounded-pill px-4 fw-bold w-100"
                            onClick={() => onRefund(ticket.id)}
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