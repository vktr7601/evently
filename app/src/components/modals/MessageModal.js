const TYPE_CONFIG = {
    info:    { bg: 'bg-primary',   icon: 'bi-info-circle-fill',     closeBtn: 'btn-outline-primary'   },
    success: { bg: 'bg-success',   icon: 'bi-check-circle-fill',    closeBtn: 'btn-outline-success'   },
    warning: { bg: 'bg-warning',   icon: 'bi-exclamation-circle-fill', closeBtn: 'btn-outline-warning' },
};

const MessageModal = ({ show, onClose, title, messages, type = 'info' }) => {
    if (!show) return null;

    const { bg, icon, closeBtn } = TYPE_CONFIG[type] ?? TYPE_CONFIG.info;

    return (
        <div className="modal fade show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }} tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content border-0 shadow rounded-4 overflow-hidden">
                    <div className={`modal-header ${bg} text-white`}>
                        <h5 className="modal-title fw-bold">
                            <i className={`bi ${icon} me-2`}></i>{title}
                        </h5>
                        <button type="button" className="btn-close btn-close-white" onClick={onClose}></button>
                    </div>
                    <div className="modal-body p-4">
                        {Array.isArray(messages) ? (
                            <ul className="mb-0">
                                {messages.map((msg, i) => (
                                    <li key={i} className="text-secondary mb-1">{msg}</li>
                                ))}
                            </ul>
                        ) : (
                            <p className="text-secondary mb-0">{messages}</p>
                        )}
                    </div>
                    <div className="modal-footer border-0">
                        <button type="button" className={`btn ${closeBtn} rounded-pill px-4`} onClick={onClose}>
                            Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MessageModal;
