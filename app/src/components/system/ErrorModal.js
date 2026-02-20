const ErrorModal = ({ show, onClose, title, messages }) => {
    if (!show) return null;

    return (
        <div className="modal fade show d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }} tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content border-0 shadow">
                    <div className="modal-header bg-danger text-white">
                        <h5 className="modal-title font-weight-bold">
                            <i className="bi bi-exclamation-triangle-fill me-2"></i> {title || 'Action Required'}
                        </h5>
                        <button type="button" className="btn-close btn-close-white" onClick={onClose}></button>
                    </div>
                    <div className="modal-body p-4">
                        {Array.isArray(messages) ? (
                            <ul className="mb-0">
                                {messages.map((msg, i) => <li key={i} className="text-secondary mb-1">{msg}</li>)}
                            </ul>
                        ) : (
                            <p className="text-secondary mb-0">{messages}</p>
                        )}
                    </div>
                    <div className="modal-footer border-0">
                        <button type="button" className="btn btn-outline-secondary rounded-pill px-4" onClick={onClose}>
                            Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ErrorModal;