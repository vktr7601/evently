const NotificationListItem = ({ data, isActive }) => {
    const { title, htmlBody, isRead, createdAt } = data;
    
    // Strip HTML for the preview snippet
    const previewText = htmlBody.replace(/<[^>]*>?/gm, '');

    return (
        <div className={`list-group-item list-group-item-action border-0 border-bottom p-3 
            ${!isRead ? 'bg-white' : 'bg-light'} 
            ${isActive ? 'bg-primary-subtle border-start border-primary border-4' : ''}`}
            style={{ cursor: 'pointer' }}
        >
            <div className="d-flex align-items-center gap-3">
                <div className={`rounded-circle d-flex align-items-center justify-content-center flex-shrink-0 
                    ${!isRead ? 'bg-primary text-white' : 'bg-secondary-subtle text-muted'}`}
                    style={{ width: '35px', height: '35px' }}>
                    <i className={`bi ${!isRead ? 'bi-bell-fill' : 'bi-bell'}`} style={{ fontSize: '0.8rem' }}></i>
                </div>

                <div className="d-flex flex-grow-1 align-items-center justify-content-between overflow-hidden">
                    <div style={{ width: '130px' }} className="flex-shrink-0">
                        <h6 className={`mb-0 text-truncate ${!isRead ? 'fw-bold text-dark' : 'text-secondary'}`}>
                            {title}
                        </h6>
                    </div>

                    <div className="flex-grow-1 px-3 overflow-hidden text-muted small text-truncate">
                        {previewText}
                    </div>

                    <div className="flex-shrink-0 ms-auto">
                        <small className="text-muted" style={{ fontSize: '0.7rem' }}>
                            {new Date(createdAt).toLocaleDateString()}
                        </small>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default NotificationListItem;